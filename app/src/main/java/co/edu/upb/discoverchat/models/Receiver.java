package co.edu.upb.discoverchat.models;

import android.database.Cursor;

import org.json.JSONObject;

import java.util.regex.Pattern;

import co.edu.upb.discoverchat.data.db.base.DbBase;

public class Receiver extends Model {
    private String name;
    private String phone;

    public Receiver(){

    }
    public Receiver(Cursor c) {
        this.setId(c.getInt(c.getColumnIndex(DbBase.KEY_ID)));
        this.setName(c.getString(c.getColumnIndex(DbBase.FIELD_NAME)));
        this.setPhone(c.getString(c.getColumnIndex(DbBase.FIELD_PHONE)));
    }
    public Receiver(JSONObject jsonObject){
        super(jsonObject);
    }
    public long getId() {return id;}
    public String getName() {
        if(name!=null)
            return name;
        else
            return phone;
    }
    public Receiver setName(String name) {
        this.name = name;
        return this;
    }
    public String getPhone() {
        return phone;
    }
    public Receiver setPhone(String phone) {
        phone = phone.replaceAll(" ","").trim();
        if(phone.contains(Pattern.quote("+57"))){
            phone = phone.substring(3);
        }
        this.phone = phone;
        return this;
    }

    @Override
    protected void parseJsonObject(JSONObject json) {

    }
}