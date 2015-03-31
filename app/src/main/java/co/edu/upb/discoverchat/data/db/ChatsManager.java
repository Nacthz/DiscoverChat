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

/**
 * Created by hatsumora on 30/03/15.
 * This class will handle all the data about the chats
 */
public class ChatsManager extends DbBase implements DbInterface {
    public static final String TBL_LOCAL = TBL_CHATS;

    public ChatsManager(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    // Adding new chat
    public long add(Model model) {
        Chat chat = (Chat)model;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, chat.getName());
        values.put(KEY_ROOM_IMAGE_PATH,chat.getRoomImagePath());

        long id = db.insert(TBL_CHATS, null, values);
        chat.setId(id);
        db.close();

        return id;
    }

    // Getting single chat
    public Chat get(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TBL_LOCAL,null, KEY_ID + "= ? ", new String[]{String.valueOf(id)},null,null,null,null);
        if(c != null){
            c.moveToFirst();
            return loadChatFromCursor(c);
        }
        return null;
    }

    // Getting All chats
    public List<Chat> getAll() {
        List<Chat> chats = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+ TBL_CHATS;
        SQLiteDatabase db =  this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst())
            do
                chats.add(loadChatFromCursor(c));
            while (c.moveToNext());

        db.close();
        return chats;
    }

    // Getting chats Count
    public int getAllCount() {
        return getAll().size();
    }
    // Updating single chat
    //public int updateChat(Chat chat) {return 0;}

    // Deleting single chat
    public int delete(Model model) {
        Chat chat = (Chat)model;
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TBL_CHATS,KEY_ID + " =? ", new String[]{String.valueOf(chat.getId())});
    }

    private Chat loadChatFromCursor(Cursor c){
        Chat chat = new Chat(c);
        ReceiversManager receiversManager = new ReceiversManager(context);
        chat.setReceivers(receiversManager.getAllForChat(chat));
        return chat;
    }

    public String getLastMessageForChat(Chat chat){
        //TODO
        return "Chingones";
    }
    public String getLastDateForChat(Chat chat){
        //TODO
        return "Hoy";
    }
}
