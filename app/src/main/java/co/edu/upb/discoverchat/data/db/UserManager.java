package co.edu.upb.discoverchat.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import co.edu.upb.discoverchat.data.db.base.DbBase;
import co.edu.upb.discoverchat.models.Model;
import co.edu.upb.discoverchat.models.User;

/**
 * Created by hatsumora on 31/03/15.
 * Handle the user data and his image as receiver.
 */
public class UserManager extends DbBase{
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

        values = new ContentValues();
        values.put(KEY_ID, 1);
        values.put(FIELD_PHONE, user.getPhone());
        values.put(FIELD_NAME, "Yo");

        db.insertWithOnConflict(TBL_RECEIVERS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();

        return id;
    }

    @Override
    public int delete(Model model) {
        return 0;
    }

    @Override
    public String getTable() {
        return TBL_USER;
    }

    @Override
    protected Class getModelClass() {
        return User.class;
    }
}
