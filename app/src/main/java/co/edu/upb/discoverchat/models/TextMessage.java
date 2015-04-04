package co.edu.upb.discoverchat.models;


import android.database.Cursor;

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
