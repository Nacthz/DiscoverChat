package co.edu.upb.discoverchat.models;

import android.content.Context;
import android.database.Cursor;

import org.json.JSONObject;

import co.edu.upb.discoverchat.data.db.ReceiversManager;

public abstract class Message extends Model{
    private long chat_id;
    private long receiver_id;
    public enum Type{
        TEXT,
        IMAGE
    }
    public Message() {
    }

    public Message(JSONObject json) {
        super(json);
    }

    public long getChat_id() {
        return chat_id;
    }
    public void setChat_id(long chat_id) {
        this.chat_id = chat_id;
    }
    public long getReceiver_id() {
        return receiver_id;
    }
    public void setReceiver_id(long receiver_id) {
        this.receiver_id = receiver_id;
    }

    public Receiver whoIsSender(Context context){
        Receiver receiver = null;
        ReceiversManager receiversManager = new ReceiversManager(context);
        receiver = receiversManager.whoSend(this);
        return receiver;
    }

    public boolean itsMine(Context context){
        Receiver sender = whoIsSender(context);
        if(sender!=null)
            return sender.getId() < 2;
        return true;
    }
    public Message(Cursor c) {
        super(c);
    }

    @Override
    protected void parseJsonObject(JSONObject json) {
        //TODO
    }

    public abstract Type getType();
}