package com.viatom.checkmelib.bluetooth;

import com.viatom.checkmelib.utils.CRCUtils;
import com.viatom.checkmelib.utils.LogUtils;

public class StartWriteAckPkg {
	private byte cmd = 0;
	private byte errCode = -2;
	
	public StartWriteAckPkg(byte[] buf) {
		if(buf.length!=BTConstant.COMMON_ACK_PKG_LENGTH){
			LogUtils.d("startWriteAck length error");
			return;
		}
		if(buf[0]!=(byte)0x55){
			LogUtils.d("startWriteAck head error");
			return;
		}else if ((cmd = buf[1])!=BTConstant.ACK_CMD_OK || buf[2]!=~BTConstant.ACK_CMD_OK) {
			LogUtils.d("startWriteAck cmd word error");
			return;
		}else if (buf[buf.length-1]!=CRCUtils.calCRC8(buf)) {
			LogUtils.d("startWriteAck CRC error");
			return;
		}
		
	}

	public byte getCmd() {
		return cmd;
	}

	public byte getErrCode() {
		return errCode;
	}
	
}	
