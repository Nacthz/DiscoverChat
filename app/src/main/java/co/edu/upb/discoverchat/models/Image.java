package co.edu.upb.discoverchat.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.regex.Pattern;

import co.edu.upb.discoverchat.data.db.ImageMessagesManager;
import co.edu.upb.discoverchat.data.db.base.DbBase;

/**
 * Created by hatsumora on 10/04/15.
 */
public class Image extends Model {
    private String path;
    private String url;
    private double latitude;
    private double longitude;
    private Bitmap bitmap;

    public String getPath() {
        return path;
    }
    public Image setPath(String path) {
        this.path = path;
        return this;
    }
    public String getUrl() {
        return url;
    }
    public Image setUrl(String url) {
        this.url = url;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }
    public Image setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }
    public double getLongitude() {return longitude;}
    public Bitmap getBitmap() {
        if(bitmap==null)
            bitmap = BitmapFactory.decodeFile(path);
        return bitmap;
    }


    public Image setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    protected void parseJsonObject(JSONObject json) {

    }

}
