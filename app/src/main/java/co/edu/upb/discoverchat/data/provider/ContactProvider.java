package co.edu.upb.discoverchat.data.provider;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

/**
 * Created by hatsumora on 31/03/15.
 */
public class ContactProvider {

    public ContactProvider(Context context){
        this.context = context;
    }
    String[] dataSearched = {
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };
    Context context;
    public Cursor getAllContacts(){

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection    = new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        Cursor c = context.getContentResolver().query(uri, projection, null, null, null);

        return c;
    }

    public String nameOfPhone(String phone){
        //todo
        return null;
    }
}
