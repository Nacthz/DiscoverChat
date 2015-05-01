package co.edu.upb.discoverchat.data.db.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.TreeSet;

import co.edu.upb.discoverchat.data.db.ChatsManager;
import co.edu.upb.discoverchat.data.db.ImageMessagesManager;
import co.edu.upb.discoverchat.data.db.ReceiversManager;
import co.edu.upb.discoverchat.data.db.TextMessagesManager;
import co.edu.upb.discoverchat.exceptions.NotImplementedMethod;
import co.edu.upb.discoverchat.exceptions.NotCalleableMethod;
import co.edu.upb.discoverchat.models.Chat;
import co.edu.upb.discoverchat.models.Message;
import co.edu.upb.discoverchat.models.Model;

/**
 * Created by hatsumora on 10/04/15.
 */
public  class MessageManager extends DbBase{
    public MessageManager(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }
    public MessageManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }
    public String getTable() {
        throw new NotCalleableMethod();
    }

    protected Class getModelClass() {
        throw new NotCalleableMethod();
    }

    public long add(Model model) {
        throw new NotCalleableMethod();
    }

    public int delete(Model model) {
        throw new NotCalleableMethod();
    }

    public TreeSet<Message> getAllMessagesForChat(Chat chat){
        TreeSet<Message> messages = new TreeSet<Message>();
        TextMessagesManager textMessagesManager = new TextMessagesManager(this.context);
        messages.addAll(textMessagesManager.getAllBy(KEY_CHAT_ID,chat.getId()));

        ImageMessagesManager imageMessagesManager = new ImageMessagesManager(this.context);
        messages.addAll(imageMessagesManager.getAllBy(KEY_CHAT_ID,chat.getId()));
        return  messages;
    }

    public String phoneDestination(Message m) {
        long chatId = m.getChat_id();
        ReceiversManager receiversManager = new ReceiversManager(context);
        //todo extend for group chats
        return receiversManager.getAllForChat(new ChatsManager(context).<Chat>get(chatId)).get(0).getPhone();
    }
}
