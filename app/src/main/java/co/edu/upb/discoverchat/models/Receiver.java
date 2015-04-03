package co.edu.upb.discoverchat.models;

import android.database.Cursor;

import org.json.JSONObject;

import co.edu.upb.discoverchat.data.db.base.DbBase;

public class Receiver extends Model {
    private long chatId;
    private String name;
    private String phone;

    public Receiver(){

    }
    public Receiver(Cursor c) {
        this.setId(c.getInt(c.getColumnIndex(DbBase.KEY_ID)));
        this.setName(c.getString(c.getColumnIndex(DbBase.FIELD_NAME)));
        this.setChatId(c.getInt(c.getColumnIndex(DbBase.KEY_CHAT_ID)));
        this.setPhone(c.getString(c.getColumnIndex(DbBase.FIELD_PHONE)));
    }
    public Receiver(JSONObject jsonObject){
        super(jsonObject);
    }
    public long getId() {return id;}

    public Receiver setId(long id) {
        this.id = id;
        return this;
    }
    public long getChatId() {
        return chatId;
    }
    public Receiver setChatId(long chatId) {
        this.chatId = chatId;
        return this;
    }
    public String getName() {
        return name;
    }
    public Receiver setName(String name) {
        this.name = name;
        return this;
    }
    public String getPhone() {
        return phone;
    }
    public Receiver setPhone(String phone) {
        this.phone = phone;
        JSONObject jsonObject=new JSONObject();
        Receiver r = new Receiver(jsonObject);
        return this;
    }

    @Override
    protected void parseJsonObject(JSONObject json) {

    }
}