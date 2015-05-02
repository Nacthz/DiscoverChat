package co.edu.upb.discoverchat.data.provider;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

/**
 * Created by hatsumora on 31/03/15.
 * This class will retrive all info thats needed from contactProvider
 */
public class ContactProvider {

    public ContactProvider(Context context){
        this.context = context;
    }

    Context context;
    public Cursor getAllContacts(){

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection    = new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};
        return  context.getContentResolver().query(uri, projection, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC");
    }

    public String nameOfPhone(String phone){
        Cursor c = getAllContacts();
        if(c.moveToFirst()){
            int indexName = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            int indexNumber = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            do
                if(c.getString(indexNumber).trim().replaceAll(" ","").contains(phone))
                    return c.getString(indexName);
            while (c.moveToNext());
        }
        return null;
    }
}
