package co.edu.upb.discoverchat.data.web.base;
import android.content.Context;

import com.loopj.android.http.*;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.json.JSONObject;

import co.edu.upb.discoverchat.data.db.base.DbBase;

/**
 * Created by hatsumora on 1/04/15.
 * Base for all rest clients
 */
public class RestClient {
    private static final String protocol = "http://";
    private static final String urlBase = "drake.ngrok.com";
    private static final String mods = ":80/";
    private static final String APPLICATION_JSON = "application/json";
    private static final String registrationPath = "users.json";
    private static final String shipMessagePath = "api/messages/ship.json";
    private static final String ShipImagePath = "api/messages/upload_image.json";
    private static final String closestImagesPath = "api/images/closest";

    protected static final String FIELD_IMAGE = "image";
    protected static final String FIELD_DESTINATION = "to";
    protected static final String FIELD_TYPE = DbBase.FIELD_TYPE;

    public static String getRegistrationPath(){
        return registrationPath;}
    public static String getShipMessagePath(){
        return shipMessagePath;};
    public static String getImageMessagePath(){
        return ShipImagePath;};

    public static String getClosestImagesPath() {
        return closestImagesPath;}

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }
    public static void post(String url, HttpEntity entity, final HandlerJsonRequest responseHandler){
        client.post(null,getAbsoluteUrl(url),entity, APPLICATION_JSON, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if(responseHandler != null)
                    responseHandler.handleResponse(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                throwable.printStackTrace();
                if(responseHandler != null)
                    responseHandler.handleError(errorResponse.toString());
            }
        });
    }

    public static void put(String url, HttpEntity entity, final HandlerJsonRequest responseHandler){
        client.post(null,getAbsoluteUrl(url),entity,APPLICATION_JSON,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if(responseHandler!=null){
                    responseHandler.handleResponse(response);
                }
            }
        });
    }

    public static void post(String url, RequestParams params, final HandlerJsonRequest responseHandler) {
        client.post(getAbsoluteUrl(url), params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if(responseHandler != null)
                    responseHandler.handleResponse(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                throwable.printStackTrace();
                if(responseHandler != null)
                    responseHandler.handleError(errorResponse.toString());
            }
        });
    }
//    public static void post(Context)
    protected static String getAbsoluteUrl(String relativeUrl) {
        return protocol+urlBase+ mods +relativeUrl;
    }
}
