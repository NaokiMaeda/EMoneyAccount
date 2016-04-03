package lucky.emoneyaccount.Parser;

/**
 * Created by lucky on 16/04/03.
 */
public class PollingParser {
	private	int		responseLength;
	private	byte	responseCode;
	private	byte[]	IDm;
	private	byte[]	PMm;
	private	byte[]	requestData;

	public PollingParser(){
		IDm = new byte[8];
		PMm = new byte[8];
		requestData = new byte[2];
	}

	public PollingParser(byte[] responseData){
		this();
		parse(responseData);
	}

	public void parse(byte[] responseData){
		responseLength	= responseData[0] & 0xff;
		responseCode	= responseData[1];
		System.arraycopy(responseData , 2 , IDm , 0 , 8);
		System.arraycopy(responseData, 10, PMm, 0, 8);
		if(responseLength == 20){
			System.arraycopy(responseData , 18 , requestData , 0 , 2);
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

	public byte[] getPMm(){
		return PMm;
	}

	public byte[] getRequestData(){
		return requestData;
	}
}
