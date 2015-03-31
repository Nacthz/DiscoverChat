package co.edu.upb.discoverchat.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.apache.http.MethodNotSupportedException;

import java.util.ArrayList;
import java.util.List;

import co.edu.upb.discoverchat.data.db.base.DbBase;
import co.edu.upb.discoverchat.data.db.base.DbInterface;
import co.edu.upb.discoverchat.models.Model;
import co.edu.upb.discoverchat.models.Receiver;
import co.edu.upb.discoverchat.models.User;

/**
 * Created by hatsumora on 31/03/15.
 */
public class UserManager extends DbBase implements DbInterface {
    public UserManager(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public long add(Model model) {
        User user = (User)model;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_GOOGLE_CLOUD_MESSAGE, user.getGoogle_gcm_code());
        values.put(KEY_AUTHENTICATION_TOKEN, user.getAuthentication_token());

        long id = db.insert(TBL_CHATS, null, values);
        user.setId(id);
        db.close();

        return id;
    }

    public Model get(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TBL_USER,null, KEY_ID + "= ? ", new String[]{String.valueOf(id)},null,null,null,null);
        if(c != null){
            c.moveToFirst();
            return loadUserFromCursor(c);
        }
        return null;
    }

    private User loadUserFromCursor(Cursor c) {
        return new User(c);
    }

    public List getAll() {

        List<User> users = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+ TBL_RECEIVERS;
        SQLiteDatabase db =  this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst())
            do
                users.add(loadUserFromCursor(c));
            while (c.moveToNext());

        db.close();
        throw new SecurityException();
        //return users;
    }

    @Override
    public int getAllCount() {
        return 0;
    }

    @Override
    public int delete(Model model) {
        return 0;
    }
}
