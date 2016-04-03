package lucky.emoneyaccount.Command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by lucky on 16/04/03.
 */
public class ReadWOECommand {
	protected 	 		ByteArrayOutputStream	baos;
	protected  			byte	commandLength;
	protected	final	byte	commandCode = 0x06;
	protected 			byte[]	IDm;
	protected 			int		serviceNum;
	protected 			byte[]	serviceCodeList;
	protected 			int		blockNum;
	protected 			byte[]	blockList;

	public ReadWOECommand(){
		commandLength = 0x01;
		IDm = null;
		serviceCodeList = null;
		blockList = null;
	}

	public ReadWOECommand(byte[] IDm){
		this();
		setIDm(IDm);
	}

	public byte[] createCommand(byte[] blockList){
		setBlockList(blockList);
		return assembleCommand();
	}

	public byte[] createCommand(byte[] IDm , byte[] serviceCodeList , byte[] blockList){
		setIDm(IDm);
		setServiceCodeList(serviceCodeList);
		setBlockList(blockList);
		return assembleCommand();
	}

	public void setIDm(byte[] IDm){
		if(IDm.length != 8){
			this.IDm = null;
			return;
		}
		this.IDm = new byte[8];
		this.IDm = IDm.clone();
	}

	public void setServiceCodeList(byte[] serviceCodeList){
		if(serviceCodeList.length < 1 || serviceCodeList.length > 32)	return;
		serviceNum = serviceCodeList.length / 2;
		this.serviceCodeList = new byte[serviceNum];
		this.serviceCodeList = serviceCodeList.clone();
	}

	public void setBlockList(byte[] blockList){
		blockNum = 0;
		for(int i = 0; i < blockList.length;){
			if((blockList[i] & 0x80) == 0x80){
				i += 2;
			}else{
				i += 3;
			}
			blockNum++;
		}
		this.blockList = new byte[blockNum];
		this.blockList = blockList.clone();
	}

	protected byte[] assembleCommand(){
		if(IDm == null || serviceCodeList == null || blockList == null) {
			return null;
		}
		byte[]	command;
		baos = new ByteArrayOutputStream();
		try {
			baos.write(commandLength);
			baos.write(commandCode);
			baos.write(IDm);
			baos.write(serviceNum);
			baos.write(serviceCodeList);
			baos.write(blockNum);
			baos.write(blockList);
			command = baos.toByteArray();
			command[0] = (byte)command.length;
		} catch (IOException e) {
			e.printStackTrace();
			command = null;
		}finally {
			try {
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return command;
	}
}
