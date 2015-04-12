package co.edu.upb.discoverchat.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import co.edu.upb.discoverchat.data.db.base.DbBase;
import co.edu.upb.discoverchat.models.Chat;
import co.edu.upb.discoverchat.models.Model;
import co.edu.upb.discoverchat.models.Receiver;

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

    // Deleting single chat
    public int delete(Model model) {
        Chat chat = (Chat)model;
        SQLiteDatabase db = getWritableDatabase();
        int res = db.delete(TBL_CHATS,KEY_ID + " =? ", new String[]{String.valueOf(chat.getId())});
        db.close();
        return res;
    }

    public Chat getOneChatFor(Receiver param){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TBL_CHATS_RECEIVERS,null, KEY_RECEIVER_ID + "= ? ", new String[]{String.valueOf(param.getId())},null,null,null,null);
        if(c.moveToFirst()){
            long id = c.getLong(0);
            return get(id);
        }
        c.close();
        db.close();
        return newForReceiver(param);
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

    public Chat newForReceiver(Receiver receiver) {
        Chat chat = new Chat();
        ArrayList<Receiver> receivers = new ArrayList<>();
        receivers.add(receiver);
        chat.setReceivers(receivers);
        chat.setName(receiver.getName());
        this.add(chat);

        newChatReceiver(chat,receiver);

        return chat;
    }
    private void newChatReceiver(Chat chat, Receiver receiver){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CHAT_ID, chat.getId());
        values.put(KEY_RECEIVER_ID,receiver.getId());

        db.insert(TBL_CHATS_RECEIVERS, null, values);
        db.close();
    }

    public Chat makeConsistent(Chat chat) {
        if(chat.getId()<1){
            Receiver r = chat.getReceivers().get(0);
            long chat_id;
            ReceiversManager receiversManager = new ReceiversManager(context);
            Receiver real = receiversManager.findBy(FIELD_PHONE,r.getPhone());
            if(real==null){
                receiversManager.add(r);
                real = r;
            }
            return getOneChatFor(real);
        }
        return chat;
    }
}
