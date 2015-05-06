package co.edu.upb.discoverchat.models;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONObject;

import co.edu.upb.discoverchat.data.db.base.DbBase;

/**
 * Created by hatsumora on 10/04/15.
 *
 */
public class Image extends Model {
    private String path;
    private String url;
    private long serverID;
    private double latitude;
    private double longitude;
    private Bitmap bitmap;

    public Image(Cursor c) {
        base(c,false);
    }
    public Image(){

    }
    public Image(Cursor c, boolean flag ) {
        base(c, flag);
    }
    private void base(Cursor c, boolean flag){
        if(flag)
            this.setId(c.getLong(c.getColumnIndex(DbBase.KEY_IMAGE_ID)));
        else
            this.setId(c.getLong(c.getColumnIndex(DbBase.KEY_ID)));
        if (c.getColumnIndex(DbBase.FIELD_IMAGE_PATH)>-1)
            this.setPath(c.getString(c.getColumnIndex(DbBase.FIELD_IMAGE_PATH)));
        if (c.getColumnIndex(DbBase.FIELD_IMAGE_URL)>-1)
            this.setUrl(c.getString(c.getColumnIndex(DbBase.FIELD_IMAGE_URL)));
        if (c.getColumnIndex(DbBase.KEY_SERVER_MODEL_ID)>-1)
            this.setServerID(c.getLong(c.getColumnIndex(DbBase.KEY_SERVER_MODEL_ID)));

    }
    public long getServerID() {
        return serverID;
    }
    public void setServerID(long serverID) {
        this.serverID = serverID;
    }

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
