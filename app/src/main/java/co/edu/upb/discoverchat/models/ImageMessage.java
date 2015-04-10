package co.edu.upb.discoverchat.models;

import android.graphics.Bitmap;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by hatsumora on 10/04/15.
 */
public class ImageMessage extends Message {
    protected Date date;

    @Override
    public Type getType() {
        return Type.IMAGE;
    }

    Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Bitmap getBitmap(){
        return image.getBitmap();
    }

    public Date getDate() {
        return date;
    }
    @Override
    protected void parseJsonObject(JSONObject json) {

    }
}
