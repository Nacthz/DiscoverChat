package co.edu.upb.discoverchat.data.web.base;
import com.loopj.android.http.*;

/**
 * Created by hatsumora on 1/04/15.
 * Base for all rest clients
 */
public class RestClient {
    private static final String protocol = "http://";
    private static final String urlBase = "192.168.1.11";
    private static final String portNumber = ":3000/";

    private static final String registrationPath = "users.json";
    private static String shipMessagePath = "messages/ship.json";

    public static String getRegistrationPath(){
        return registrationPath;}
    public static String getShipMessagePath(){
        return shipMessagePath;};

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return protocol+urlBase+portNumber;
    }
}
