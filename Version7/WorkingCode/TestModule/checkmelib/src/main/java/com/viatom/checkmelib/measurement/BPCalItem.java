package com.viatom.checkmelib.measurement;

import com.viatom.checkmelib.utils.LogUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;



/**
 * BP calibration item
 * @author zouhao
 */
public class BPCalItem {

	private int id;
	private byte calType = MeasurementConstant.BP_TYPE_NONE; //Calibration Type
	private Date calDate;
	private byte sbp;
	private short sys;
	private byte diasys;
	private byte PR;

	
	public BPCalItem() {
	}

	public BPCalItem(byte[] buf) {
		if (buf.length!=MeasurementConstant.BPCAL_ITEM_LENGHT) {
			LogUtils.d("BpCalItem length error");
			return;
		}
		//Relative calibration
	//	calType = MeasurementConstant.BP_TYPE_RE;
		
//		id = buf[0];
//		Calendar calendar = new GregorianCalendar((buf[1] & 0xFF)
//				+ ((buf[2] & 0xFF) << 8)
//		, (buf[3] & 0xFF) - 1, buf[4] & 0xFF,
//		buf[5] & 0xFF, buf[6] & 0xFF, buf[7] & 0xFF);
//		calDate = calendar.getTime();
		Calendar calendar = new GregorianCalendar((buf[0] & 0xFF)
				+ ((buf[1] & 0xFF) << 8)
				, (buf[2] & 0xFF) - 1, buf[3] & 0xFF,
				buf[4] & 0xFF, buf[5] & 0xFF, buf[6] & 0xFF);
		calDate = calendar.getTime();
		sys = (short) (((short)buf[8] << 8) | buf[7]);
		diasys = buf[9];
		PR = buf[10];
//		if(buf[11]!=0){
//			//Absolutely calibration
//			calType = MeasurementConstant.BP_TYPE_ABS;
//			sbp = buf[11];
//		}
	}

	public static BPCalItem getlatestBPItem(byte[] buf) {
		if (buf == null || buf.length % MeasurementConstant.BPCAL_ITEM_LENGHT != 0) {
			LogUtils.d("BP item buff length err!");
			return null;
		}

		int itemNum = buf.length / MeasurementConstant.BPCAL_ITEM_LENGHT;
		byte[] tempBuf = new byte[MeasurementConstant.BPCAL_ITEM_LENGHT];
		System.arraycopy(buf, (itemNum-1) * MeasurementConstant.BPCAL_ITEM_LENGHT, tempBuf, 0, MeasurementConstant.BPCAL_ITEM_LENGHT);
		return new BPCalItem(tempBuf);
	}

//	public static ArrayList<BPCalItem> getBPItemList(byte[] buf) {
//		if (buf == null || buf.length % MeasurementConstant.BPCAL_ITEM_LENGHT != 0) {
//			LogUtils.d("BP item buff length err!");
//			return null;
//		}
//
//		int itemNum = buf.length / MeasurementConstant.BPCAL_ITEM_LENGHT;
//		ArrayList<BPCalItem> bpItems = new ArrayList<BPCalItem>();
//		for (int i = 0; i < itemNum; i++) {
//			byte[] tempBuf = new byte[MeasurementConstant.BPCAL_ITEM_LENGHT];
//			System.arraycopy(buf, i * MeasurementConstant.BPCAL_ITEM_LENGHT, tempBuf, 0, MeasurementConstant.BPCAL_ITEM_LENGHT);
//			bpItems.add(new BPCalItem(tempBuf));
//		}
//		return bpItems;
//	}

	public Date getCalDate() {
		return calDate;
	}

//	public byte getCalType() {
//		return calType;
//	}

	public short getSys() {
		return sys>0 ? sys : (short) -sys;
	}

	public byte getDiaSys() {
		return diasys;
	}

	public byte getPR() {
		return PR;
	}

	public int getId() {
		return id;
	}
	
}
