package com.viatom.checkmelib.measurement;

import com.viatom.checkmelib.utils.LogUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;



/**
 * Pulse Oximeter item
 * @author zouhao
 */
public class SPO2Item implements CommonItem{

	// Origin data read from files
	private byte[] dataBuf;
	// Measuring date
	private Date date;
	// Measuring mode, internal or external
	private byte measuringMode;
	// Blood oxygen value
	private byte oxygen;
	// PR value
	private int pr;
	// PI value
	private float pi;
	// Image result, smile or cry
	private byte imgResult;

	public SPO2Item(byte[] buf) {

		if(buf.length!=MeasurementConstant.SPO2_ITEM_LENGHT){
			LogUtils.d("SPO2 buf length error");
			return;
		}
		dataBuf = buf;
		Calendar calendar = new GregorianCalendar((buf[0] & 0xFF)
				+ ((buf[1] & 0xFF) << 8), (buf[2] & 0xFF) - 1, buf[3] & 0xFF,
				buf[4] & 0xFF, buf[5] & 0xFF, buf[6] & 0xFF);
		date = calendar.getTime();
		measuringMode = (buf[7]);
		oxygen = (buf[8]);
		pr = (buf[9] & 0xFF);
		pi = (float) ((float) (buf[10] & 0xFF)) / 10;
		imgResult = (buf[11]);
	}

	public static SPO2Item getlatestSPO2Item(byte[] buf) {
		if (buf == null || buf.length % MeasurementConstant.SPO2_ITEM_LENGHT != 0) {
			LogUtils.d("SPO2 item buff length err!");
			return null;
		}

		int itemNum = buf.length / MeasurementConstant.SPO2_ITEM_LENGHT;
		ArrayList<SPO2Item> spo2Items = new ArrayList<SPO2Item>();
		byte[] tempBuf = new byte[MeasurementConstant.SPO2_ITEM_LENGHT];
		System.arraycopy(buf, (itemNum-1) * MeasurementConstant.SPO2_ITEM_LENGHT, tempBuf, 0, MeasurementConstant.SPO2_ITEM_LENGHT);
		return new SPO2Item(tempBuf);
	}

	public Date getDate() {
		return date;
	}
	public byte getMeasuringMode() {
		return measuringMode;
	}
	public byte getOxygen() {
		return oxygen;
	}
	public int getPr() {
		return pr;
	}
	public float getPi() {
		return pi;
	}
	public byte getImgResult() {
		return imgResult;
	}

	public byte[] getDataBuf() {
		return dataBuf;
	}

	@Override
	public boolean isDownloaded() {
		return true;
	}
}
