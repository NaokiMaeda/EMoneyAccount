package lucky.emoneyaccount.Command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by lucky on 16/04/03.
 */
public class SuicaReadCommand extends ReadWOECommand{
	private final	byte[]			UsageHistoryServiceCode	= {0x0f , 0x09};	//利用履歴
	private ByteArrayOutputStream	baos;

	public SuicaReadCommand(){
		super();
		setServiceCodeList(UsageHistoryServiceCode);
	}

	public SuicaReadCommand(byte[] IDm){
		super(IDm);
		setServiceCodeList(UsageHistoryServiceCode);
	}

	public byte[] createCommand(byte[] blockList){
		return super.createCommand(blockList);
	}

	public byte[] createGetHistoryCommand(int HistoryNum){
		byte[] blockList;
		baos = new ByteArrayOutputStream();
		for(int i = 0; i < HistoryNum; i++){
			baos.write(0x80);	//ブロックエレメント上位バイト
			baos.write(i);		//ブロック番号
		}
		blockList = baos.toByteArray();
		try {
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.createCommand(blockList);
	}
}
