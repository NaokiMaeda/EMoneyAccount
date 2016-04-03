package lucky.emoneyaccount.Parser;

/**
 * Created by lucky on 16/04/03.
 */
public class ReadWOEParser {
	protected int		responseLength;
	protected byte		responseCode;
	protected byte[]	IDm;
	protected byte		statusFlag1;
	protected byte		statusFlag2;
	protected int		blockNum;
	protected byte[][]	blockData;

	public ReadWOEParser(){
		IDm = new byte[8];
	}

	public ReadWOEParser(byte[] responseData){
		this();
		parse(responseData);
	}

	public void parse(byte[] responseData){
		responseLength	= responseData[0] & 0xff;
		responseCode	= responseData[1];
		if(responseCode != 0x07){
			return;
		}
		System.arraycopy(responseData , 2 , IDm , 0 , 8);
		statusFlag1 = responseData[10];
		statusFlag2 = responseData[11];
		blockNum	= responseData[12];
		blockData = new byte[blockNum][16];
		for(int i = 0; i < blockNum; i++){
			System.arraycopy(responseData , 13 + (16 * i) , blockData[i] , 0 , 16);
		}
	}

	public int getResponseLength(){
		return responseLength;
	}

	public byte getResponseCode(){
		return responseCode;
	}

	public byte[] getIDm(){
		return IDm;
	}

	public byte getStatusFlag1(){
		return statusFlag1;
	}

	public byte getStatusFlag2(){
		return statusFlag2;
	}

	public int getBlockNum(){
		return blockNum;
	}

	public byte[][] getBlockData(){
		return blockData;
	}

	public byte[] getBlockData(int index){
		return blockData[index];
	}
}
