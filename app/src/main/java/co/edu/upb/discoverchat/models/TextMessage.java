package co.edu.upb.discoverchat.models;


import android.database.Cursor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.edu.upb.discoverchat.data.db.base.DbBase;

/**
 * Created by hatsumora on 2/04/15.
 * This is the text incoming and outcoming message model
 */
public class TextMessage extends Message {

    private String content;
    protected Date date;
    public TextMessage(){

    }
    public TextMessage(Cursor c){
        this.setId(c.getLong(c.getColumnIndex(DbBase.KEY_ID)));
        this.setChat_id(c.getLong(c.getColumnIndex(DbBase.KEY_CHAT_ID)));
        this.setReceiver_id(c.getLong(c.getColumnIndex(DbBase.KEY_RECEIVER_ID)));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            this.date = dateFormat.parse(c.getString(c.getColumnIndex(DbBase.FIELD_DATE)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.setContent(c.getString(c.getColumnIndex(DbBase.FIELD_CONTENT)));
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public Type getType() {
        return Type.TEXT;
    }
}
