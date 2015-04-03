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
import co.edu.upb.discoverchat.models.Message;
import co.edu.upb.discoverchat.models.Model;
import co.edu.upb.discoverchat.models.Receiver;

/**
 * Created by hatsumora on 30/03/15.
 * This class handle all the conections whit db for the receivers model
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
        values.put(FIELD_NAME, receiver.getName());
        values.put(KEY_CHAT_ID, receiver.getChatId());
        values.put(FIELD_NAME, receiver.getName());
        values.put(FIELD_PHONE, receiver.getPhone());

        long id = db.insert(TBL_RECEIVERS, null, values);
        receiver.setId(id);
        db.close();

        return id;
    }

    @Override
    public Receiver get(int id) {
        return (Receiver)get(id, Receiver.class);
    }

    public List getAll() {
        return getAll(Receiver.class);
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
    public List<Receiver> getAllForChat(Chat chat){
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

    public Receiver whoSend(Message message) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TBL_RECEIVERS,null, KEY_ID + "= ? ", new String[]{String.valueOf(message.getReceiver_id())},null,null,null,null);
        if(c != null){
            c.moveToFirst();
            return loadReceiverFromCursor(c);
        }
        return null;

    }

    @Override
    public String getTable() {
        return TBL_CHATS_RECEIVERS;
    }
}
