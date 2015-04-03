package co.edu.upb.discoverchat.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.edu.upb.discoverchat.data.db.base.DbBase;
import co.edu.upb.discoverchat.data.db.base.DbInterface;
import co.edu.upb.discoverchat.models.Model;
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
        values.put(FIELD_EMAIL, user.getEmail());
        values.put(FIELD_GOOGLE_CLOUD_MESSAGE, user.getGoogle_gcm_code());
        values.put(KEY_AUTHENTICATION_TOKEN, user.getAuthentication_token());
        values.put(FIELD_PHONE, user.getPhone());

        long id = db.insert(TBL_USER, null, values);
        user.setId(id);
        db.close();

        return id;
    }

    @Override
    public User get(int id) {
        return (User)get(id,User.class);
    }

    public List getAll() {
        return getAll(User.class);
    }

    public int getAllCount() {
        return getAll().size();
    }

    @Override
    public int delete(Model model) {
        return 0;
    }

    @Override
    public String getTable() {
        return TBL_USER;
    }
}
