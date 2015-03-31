package co.edu.upb.discoverchat.data.db.provider;
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
    String[] dataSearched = {   ContactsContract.Contacts.DISPLAY_NAME,
                                ContactsContract.Contacts.PHOTO_URI,
                                ContactsContract.Contacts.HAS_PHONE_NUMBER};
    Context context;
    public Cursor getAllContacts(){
        Cursor c = null;
        ContentResolver resolver = context.getContentResolver();
        c = resolver.query(ContactsContract.Contacts.CONTENT_URI, dataSearched,null,null,null);

        return c;
    }
}
