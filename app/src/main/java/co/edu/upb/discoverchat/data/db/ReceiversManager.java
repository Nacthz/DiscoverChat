package co.edu.upb.discoverchat.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import co.edu.upb.discoverchat.models.Model;
import co.edu.upb.discoverchat.models.Receiver;

/**
 * Created by hatsumora on 30/03/15.
 */
public class ReceiversManager extends  DbBase implements DbInterface{
    public ReceiversManager(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public long add(Model model) {
        Receiver receiver = (Receiver)model;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, receiver.getName());
        values.put(KEY_CHAT_ID, receiver.getChatId());
        values.put(KEY_NAME, receiver.getName());
        values.put(KEY_NAME, receiver.getName());

        long id = db.insert(TBL_RECEIVERS, null, values);
        receiver.setId(id);
        db.close();

        return id;
    }

    @Override
    public Model get(int id) {
        return null;
    }

    @Override
    public List getAll() {
        return null;
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
