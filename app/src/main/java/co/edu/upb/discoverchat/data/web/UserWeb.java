package co.edu.upb.discoverchat.data.web;

import android.content.Context;

import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import co.edu.upb.discoverchat.data.web.base.HandlerJsonRequest;
import co.edu.upb.discoverchat.data.web.base.RestClient;
import co.edu.upb.discoverchat.models.User;

/**
 * Created by hatsumora on 1/04/15.
 *
 */
public class UserWeb extends RestClient{
    Context context;
    User user;
    public void registerNewUser(String email, String phone, String passwd, String confirmPasswd, String googleCloudMessage, final HandlerJsonRequest hanlder){
        user = new User();
        final JSONObject userData = new JSONObject();
        JSONObject userEnv = new JSONObject();
        StringEntity entity = null;
        try {
            userData.put("email", email);
            userData.put("password", passwd);
            userData.put("celphone", phone);
            userData.put("password_confirmation", confirmPasswd);
            userData.put("google_cloud_message",googleCloudMessage);

            userEnv.put("user",userData);
            entity = new StringEntity(userEnv.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        }catch (Exception e){
            e.printStackTrace();
        }


        post(getRegistrationPath(), entity, hanlder);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void updateUser(String regid) {

    }
}
