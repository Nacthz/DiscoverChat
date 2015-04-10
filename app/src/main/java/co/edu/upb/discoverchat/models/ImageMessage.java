package co.edu.upb.discoverchat.models;

import android.graphics.Bitmap;

import org.json.JSONObject;

/**
 * Created by hatsumora on 10/04/15.
 */
public class ImageMessage extends Message {
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
    @Override
    protected void parseJsonObject(JSONObject json) {

    }
}
