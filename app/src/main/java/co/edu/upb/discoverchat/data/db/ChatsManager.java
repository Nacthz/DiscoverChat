package co.edu.upb.discoverchat.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Method;
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
public class ChatsManager extends DbBase {

    public ChatsManager(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    // Adding new chat
    public long add(Model model) {
        Chat chat = (Chat)model;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_NAME, chat.getName());
        values.put(KEY_ROOM_IMAGE_PATH,chat.getRoomImagePath());

        long id = db.insert(TBL_CHATS, null, values);
        chat.setId(id);
        db.close();

        return id;
    }

    // Getting All chats

    @Override
    public List<Chat> getAll() {
        List<Chat> chats = getAll(Chat.class);
        for (Chat c : chats){
            fillChat(c);
        }
        return chats;
    }

    private void fillChat(Chat chat) {
        ReceiversManager receiversManager = new ReceiversManager(context);
        chat.setReceivers(receiversManager.getAllForChat(chat));
    }

    // Updating single chat
    //public int updateChat(Chat chat) {return 0 as;}

    // Deleting single chat
    public int delete(Model model) {
        Chat chat = (Chat)model;
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TBL_CHATS,KEY_ID + " =? ", new String[]{String.valueOf(chat.getId())});
    }

    @Override
    public String getTable() {
        return TBL_CHATS;
    }

    @Override
    protected Class getModelClass() {
        return Chat.class;
    }

    public String getLastMessageForChat(Chat chat){
        //TODO
        return "'Last Message'";
    }
    public String getLastDateForChat(Chat chat){
        //TODO
        return "Date";
    }
}
