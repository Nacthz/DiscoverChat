package co.edu.upb.discoverchat.data.provider;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

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

        ContentResolver resolver = context.getContentResolver();
        Cursor c = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, dataSearched,null,null,null);

        return c;
    }
}
