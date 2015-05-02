package co.edu.upb.discoverchat.models;

import android.content.Context;

import org.json.JSONObject;

import java.util.Date;

import co.edu.upb.discoverchat.data.db.ReceiversManager;

public abstract class Message extends Model{
    private long chat_id;
    private long receiver_id;
    private Receiver receiver;
    protected Date date;
    private boolean sent = false;
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
    public Message setChat_id(long chat_id) {
        this.chat_id = chat_id;
        return this;
    }
    public long getReceiver_id() {
        return receiver_id;
    }
    public Message setReceiver_id(long receiver_id) {
        this.receiver_id = receiver_id; return  this;
    }
    public Receiver getReceiver() {
        return receiver;
    }
    public Message setReceiver(Receiver receiver) {
        this.receiver = receiver;
        this.receiver_id = receiver.getId();
        return this;
    }
    public boolean isSent() {
        return sent;
    }
    public Message setSent(boolean sent) {
        this.sent = sent;
        return this;
    }
    public Date getDate() {
        return date;
    }
    public Message setDate(Date date){
        this.date = date;
        return this;
    }
    public Receiver whoIsSender(Context context){
        Receiver receiver;
        ReceiversManager receiversManager = new ReceiversManager(context);
        receiver = receiversManager.whoSend(this);
        return receiver;
    }

    public boolean itsMine(Context context){
        if(this.receiver==null)
            this.receiver = whoIsSender(context);

        return this.receiver == null || this.receiver.getId() < 2;

    }
    public abstract Type getType();
}