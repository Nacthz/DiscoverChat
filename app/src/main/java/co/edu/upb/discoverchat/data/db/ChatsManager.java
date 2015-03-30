package co.edu.upb.discoverchat.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import co.edu.upb.discoverchat.models.Chat;

/**
 * Created by hatsumora on 30/03/15.
 * This class will handle all the data about the chats
 */
public class ChatsManager extends DbBase {
    public ChatsManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    // Adding new chat
    public long addChat(Chat chat) {
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
    public Chat getChat(int id) {
        Chat chat = new Chat();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TBL_CHATS,null, KEY_ID + "= ? ", new String[]{String.valueOf(id)},null,null,null,null);
        if(c != null){
            c.moveToFirst();
            chat.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            chat.setName(c.getString(c.getColumnIndex(KEY_NAME)));
            chat.setRoomImagePath(c.getString(c.getColumnIndex(KEY_ROOM_IMAGE_PATH)));
            // TODO get receivers
            return chat;
        }
        return null;
    }

    // Getting All chats
    public List<Chat> getAllChats() {
        return null;
    }

    // Getting chats Count
    public int getChatsCount() {return 0;}
    // Updating single chat
    public int updateChat(Chat chat) {return 0;}

    // Deleting single chat
    public Chat deleteChat(Chat chat) {return null;}
}
