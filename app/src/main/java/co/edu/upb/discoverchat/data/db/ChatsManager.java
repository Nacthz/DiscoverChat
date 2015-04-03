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
        values.put(FIELD_NAME, chat.getName());
        values.put(KEY_ROOM_IMAGE_PATH,chat.getRoomImagePath());

        long id = db.insert(TBL_CHATS, null, values);
        chat.setId(id);
        db.close();

        return id;
    }

    @Override
    public Chat get(int id) {
        return (Chat)get(id,Chat.class);
    }

    // Getting All chats
    public List<Chat> getAll() {
        return getAll(Chat.class);
    }

    //** Getting chats Count
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

    @Override
    public String getTable() {
        return TBL_CHATS;
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
