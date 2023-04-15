package com.viatom.checkmelib.bluetooth;


import com.viatom.checkmelib.utils.CRCUtils;

public class EndReadPkg {
	byte[] buf;
	
	public EndReadPkg() {
		buf = new byte[BTConstant.COMMON_PKG_LENGTH];
		buf[0] = (byte)0xAA;
		buf[1] = BTConstant.CMD_WORD_END_READ;
		buf[2] = ~BTConstant.CMD_WORD_END_READ;
		buf[3] = 0;//Package number, the default is 0
		buf[4] = 0;
		buf[5] = 0;//data chunk size, the default is 0
		buf[6] = 0;
		buf[buf.length-1] = CRCUtils.calCRC8(buf);
	}

	public byte[] getBuf() {
		return buf;
	}
}
