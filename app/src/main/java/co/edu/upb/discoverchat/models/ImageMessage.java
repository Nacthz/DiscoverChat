package co.edu.upb.discoverchat.models;

import android.database.Cursor;
import android.graphics.Bitmap;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by hatsumora on 10/04/15.
 */
public class ImageMessage extends Message {
    protected Date date;
    Image image;

    public ImageMessage(Bitmap bitmap){
        image = new Image();
        image.setBitmap(bitmap);
    }

    public ImageMessage() {
        image = new Image();
    }

    @Override
    public Type getType() {
        return Type.IMAGE;
    }

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
