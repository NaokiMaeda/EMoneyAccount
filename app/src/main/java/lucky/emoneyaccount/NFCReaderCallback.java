package lucky.emoneyaccount;

import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.util.Log;

/**
 * Created by lucky on 16/02/25.
 */
public class NFCReaderCallback implements NfcAdapter.ReaderCallback{
    @Override
    public void onTagDiscovered(Tag tag) {
        Log.d("IDm" , tag.getId().toString());
    }
}
