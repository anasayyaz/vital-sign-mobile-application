package com.viatom.checkmelib.bluetooth;


import com.viatom.checkmelib.utils.CRCUtils;
import com.viatom.checkmelib.utils.LogUtils;

public class EndWriteAckPkg {
	private byte cmd;
	private byte errCode = -2;
	
	public EndWriteAckPkg(byte[] buf) {
		if(buf.length!=BTConstant.COMMON_ACK_PKG_LENGTH){
			LogUtils.d("EndWriteAckPkg length error");
			return;
		}
		if(buf[0]!=(byte)0x55){
			LogUtils.d("EndWriteAckPkg head error");
			return;
		}else if ((cmd = buf[1]) != BTConstant.ACK_CMD_OK || buf[2] != ~BTConstant.ACK_CMD_OK) {
			LogUtils.d("EndWriteAckPkg cmd word error");
			return;
		}else if (buf[buf.length-1]!=CRCUtils.calCRC8(buf)) {
			LogUtils.d("EndWriteAckPkg CRC error");
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
