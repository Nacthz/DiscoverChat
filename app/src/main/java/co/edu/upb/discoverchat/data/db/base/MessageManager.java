package co.edu.upb.discoverchat.data.db.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import co.edu.upb.discoverchat.data.db.ChatsManager;
import co.edu.upb.discoverchat.data.db.ReceiversManager;
import co.edu.upb.discoverchat.models.Chat;
import co.edu.upb.discoverchat.models.Message;

/**
 * Created by hatsumora on 10/04/15.
 */
public abstract class MessageManager extends DbBase{
    public MessageManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public String phoneDestination(Message m) {
        long chatId = m.getChat_id();
        ReceiversManager receiversManager = new ReceiversManager(context);
        //todo extend for group chats
        return receiversManager.getAllForChat(new ChatsManager(context).<Chat>get(chatId)).get(0).getPhone();
    }


}
