package lucky.emoneyaccount.Command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by lucky on 16/04/02.
 */
public class PollingCommand {
	private ByteArrayOutputStream	baos;

	private	final	byte	commandLength	= 0x06;
	private final	byte	commandCode		= 0x00;
	private			byte[]	systemCode;
	private			byte	requestCode;
	private 		byte	timeSlot;

	public byte[] createCommand(){
		systemCode		= new byte[] {(byte)0xff , (byte)0xff};
		requestCode		= 0x01;
		timeSlot		= 0x00;
		return assembleCommand();
	}

	public byte[] createCommand(byte[] systemCode){
		this.systemCode	= systemCode;
		requestCode		= 0x00;
		timeSlot		= 0x00;
		return assembleCommand();
	}

	public byte[] createCommand(byte[] systemCode , byte requestCode , byte timeSlot){
		this.systemCode		= systemCode;
		this.requestCode	= requestCode;
		this.timeSlot		= timeSlot;
		return assembleCommand();
	}

	private byte[] assembleCommand(){
		byte[] command;
		baos = new ByteArrayOutputStream();
		try {
			baos.write(commandLength);
			baos.write(commandCode);
			baos.write(systemCode);
			baos.write(requestCode);
			baos.write(timeSlot);
			command = baos.toByteArray();
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
