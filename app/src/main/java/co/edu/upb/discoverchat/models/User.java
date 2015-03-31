package co.edu.upb.discoverchat.models;

import android.database.Cursor;

import java.util.HashMap;

import co.edu.upb.discoverchat.data.db.base.DbBase;

/**
 * Created by hatsumora on 30/03/15.
 */
public class User implements Model {
    private long id;
    private String email;
    private String phone;
    private String google_gcm_code;
    private String authentication_token;
    private String path_to_image;

    public User(){}
    public User(Cursor c) {
        this.setId(c.getLong(c.getColumnIndex(DbBase.KEY_ID)));
        this.setGoogle_gcm_code(c.getString(c.getColumnIndex(DbBase.KEY_GOOGLE_CLOUD_MESSAGE)));
        this.setPhone(c.getString(c.getColumnIndex(DbBase.KEY_PHONE)));
        this.setAuthentication_token(c.getString(c.getColumnIndex(DbBase.KEY_EMAIL)));
        this.setEmail(c.getString(c.getColumnIndex(DbBase.KEY_EMAIL)))  ;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getGoogle_gcm_code() {
        return google_gcm_code;
    }
    public void setGoogle_gcm_code(String google_gcm_code) {
        this.google_gcm_code = google_gcm_code;
    }
    public String getAuthentication_token() {
        return authentication_token;
    }
    public void setAuthentication_token(String authentication_token) {
        this.authentication_token = authentication_token;
    }
    public String getPath_to_image() {
        return path_to_image;
    }
    public void setPath_to_image(String path_to_image) {
        this.path_to_image = path_to_image;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int register(HashMap<String, Object> data){
        // TODO Not implemented
        return 0;
    }
    public int logIn(String email, String password){
        // TODO Not implemented
        return 0;
    }
    @Override
    public String toJsonString() {
        return null;
    }

    @Override
    public Model newFromJsonString(String model) {
        return null;
    }
}
