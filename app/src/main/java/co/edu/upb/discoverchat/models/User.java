package co.edu.upb.discoverchat.models;

import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import co.edu.upb.discoverchat.data.db.base.DbBase;

/**
 * Created by hatsumora on 30/03/15.
 */
public class User extends Model {

    private static final String SERVER_TAG_EMAIL ="email";
    private static final String SERVER_TAG_PHONE ="celphone";
    private static final String SERVER_TAG_GCM ="google_cloud_message";
    private static final String SERVER_TAG_TOKEN ="authentication_token";


    private String email;
    private String phone;
    private String google_gcm_code;
    private String authentication_token;
    private String path_to_image;

    public User(){}
    public User(Cursor c) {
        this.setId(c.getLong(c.getColumnIndex(DbBase.KEY_ID)));
        this.setGoogle_gcm_code(c.getString(c.getColumnIndex(DbBase.FIELD_GOOGLE_CLOUD_MESSAGE)));
        this.setPhone(c.getString(c.getColumnIndex(DbBase.FIELD_PHONE)));
        this.setAuthentication_token(c.getString(c.getColumnIndex(DbBase.FIELD_EMAIL)));
        this.setEmail(c.getString(c.getColumnIndex(DbBase.FIELD_EMAIL)))  ;
    }

    public User(JSONObject response) {
        super(response);
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

    public void ensureGCM(){

    }

    @Override
    protected void parseJsonObject(JSONObject json) {
        try {
            this.setEmail(json.getString(SERVER_TAG_EMAIL));
            this.setAuthentication_token(json.getString(SERVER_TAG_TOKEN));
            this.setGoogle_gcm_code(json.getString(SERVER_TAG_GCM));
            this.setPhone(json.getString(SERVER_TAG_PHONE));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
