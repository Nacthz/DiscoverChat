package co.edu.upb.discoverchat.models;

import android.graphics.Bitmap;

import org.json.JSONObject;

/**
 * Created by hatsumora on 10/04/15.
 */
public class Image extends Model {
    private String path;
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
    public double getLatitude() {
        return latitude;
    }
    public Image setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }
    public double getLongitude() {return longitude;}
    public Bitmap getBitmap() {return bitmap;}


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
