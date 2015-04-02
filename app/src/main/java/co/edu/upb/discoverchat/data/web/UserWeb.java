package co.edu.upb.discoverchat.data.web;

import android.app.Activity;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.util.HashMap;

import co.edu.upb.discoverchat.data.provider.GoogleCloudMessage;
import co.edu.upb.discoverchat.data.web.base.RestClient;
import co.edu.upb.discoverchat.models.User;

/**
 * Created by hatsumora on 1/04/15.
 *
 */
public class UserWeb {
    AsyncHttpClient client = new AsyncHttpClient();

    public String registerNewUser(String email, String phone, String passwd, String confirmPasswd, String googleCloudMessage){
        JSONObject userData = new JSONObject();
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
        }catch (Exception e){}
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        RequestParams params = new RequestParams();

        final User user = new User();
        RestClient.post(RestClient.getRegistrationPath(), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                user.setEmail(response.toString());
            }
        });
        return user.getEmail();
    }
}
