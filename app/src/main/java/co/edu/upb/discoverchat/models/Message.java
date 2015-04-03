package co.edu.upb.discoverchat.models;

import android.database.Cursor;

import org.json.JSONObject;

/**
 * Created by hatsumora on 2/04/15.
 */
public abstract class Message extends Model{
    public enum Type{
        TEXT,
        IMAGE
    }
    public Message() {
    }

    public Message(JSONObject json) {
        super(json);
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