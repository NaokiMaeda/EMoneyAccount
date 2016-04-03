package lucky.emoneyaccount;

import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.util.Log;

import java.io.IOException;

import lucky.emoneyaccount.Command.SuicaReadCommand;
import lucky.emoneyaccount.Parser.ReadWOEParser;

/**
 * Created by lucky on 16/02/25.
 */
public class NFCFReaderCallback implements NfcAdapter.ReaderCallback{
    private byte[]	command;
	private	byte[]	response;

    @Override
    public void onTagDiscovered(Tag tag) {
        byte[] idm = tag.getId();
		Log.d("IDm" , convertByteToString(idm));
        NfcF nfc = NfcF.get(tag);
        try {
            nfc.connect();
			SuicaReadCommand suica = new SuicaReadCommand(idm);
			response = nfc.transceive(suica.createGetHistoryCommand(10));
			ReadWOEParser parser = new ReadWOEParser(response);
		} catch (IOException e) {
            e.printStackTrace();
        }
    }

	private String convertByteToString(byte[] response){
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0; i < response.length; i++){
			int upperByte = (response[i] & 0xff) >> 4;
			int lowerByte = response[i] & 0x0f;
			stringBuilder.append(Integer.toHexString(upperByte));
			stringBuilder.append(Integer.toHexString(lowerByte));
			stringBuilder.append(":");
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		return stringBuilder.toString();
	}

}
