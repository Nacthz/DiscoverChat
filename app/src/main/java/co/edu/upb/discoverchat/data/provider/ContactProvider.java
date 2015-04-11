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

        /*ContentResolver resolver = context.getContentResolver();

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection    = new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        Cursor c = resolver.query(uri, projection, null, null, null);
        //int indexName = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        //int indexNumber = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

        return c;*/
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection    = new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        Cursor c = context.getContentResolver().query(uri, projection, null, null, null);

        int indexName = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int indexNumber = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        int cntRgs = c.getCount();
        String[] arr = new String [cntRgs+1];
        int i=0;



        if (c.moveToFirst()) {
            do {
                if(c.getString(indexNumber).substring(0,1).equals("+")){
                    arr[i] = c.getString(indexName)+"\n"+c.getString(indexNumber).substring(3);
                }
                else{
                    arr[i] = c.getString(indexName)+"\n"+c.getString(indexNumber);
                }
                i++;
            } while(c.moveToNext());
        }
        return c;
    }

    public String nameOfPhone(String phone){
        //todo
        return null;
    }
}
