package co.edu.upb.discoverchat.data.web.base;

import org.json.JSONObject;

/**
 * Created by hatsumora on 2/04/15.
 */
public interface HandlerJsonRequest {
    public void handleResponse(JSONObject response);
    public void handleError(String err);
}
