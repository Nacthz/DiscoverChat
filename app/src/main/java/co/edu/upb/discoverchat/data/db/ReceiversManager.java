package co.edu.upb.discoverchat.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import co.edu.upb.discoverchat.data.db.base.DbBase;
import co.edu.upb.discoverchat.data.db.base.DbInterface;
import co.edu.upb.discoverchat.models.Chat;
import co.edu.upb.discoverchat.models.Model;
import co.edu.upb.discoverchat.models.Receiver;

/**
 * Created by hatsumora on 30/03/15.
 */
public class ReceiversManager extends DbBase implements DbInterface {
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
        values.put(KEY_PHONE, receiver.getPhone());

        long id = db.insert(TBL_RECEIVERS, null, values);
        receiver.setId(id);
        db.close();

        return id;
    }

    @Override
    public Model get(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TBL_RECEIVERS,null, KEY_ID + "= ? ", new String[]{String.valueOf(id)},null,null,null,null);
        if(c != null){
            c.moveToFirst();
            return loadReceiverFromCursor(c);
        }
        return null;
    }


    public List getAll() {
        List<Receiver> receiverList = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+ TBL_RECEIVERS;
        SQLiteDatabase db =  this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst())
            do
                receiverList.add(loadReceiverFromCursor(c));
            while (c.moveToNext());

        db.close();
        return receiverList;
    }

    /*
    * query(
    *    java.lang.String table,
         java.lang.String[] columns,
         java.lang.String selection,
         java.lang.String[] selectionArgs,
         java.lang.String groupBy,
         java.lang.String having,
         java.lang.String orderBy
         */
    public List getAllForChat(Chat chat){
        List<Receiver> receiverList = new ArrayList<>();
        SQLiteDatabase db =  this.getReadableDatabase();
        Cursor c = db.query(TBL_RECEIVERS,null,KEY_CHAT_ID+" = ?", new String[]{String.valueOf(chat.getId())},null,null,null);
        if(c.moveToFirst())
            do
                receiverList.add(loadReceiverFromCursor(c));
            while (c.moveToNext());
        db.close();
        return receiverList;
    }

    public int getAllCount() {
        return getAll().size();
    }

    public int delete(Model model) {
        Receiver receiver = (Receiver)model;
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TBL_RECEIVERS, KEY_ID + " =? ", new String[]{String.valueOf(receiver.getId())});
    }

    private Receiver loadReceiverFromCursor(Cursor c){
        return new Receiver(c);
    }
}
