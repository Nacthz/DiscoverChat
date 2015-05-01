package co.edu.upb.discoverchat.models;

import android.database.Cursor;
import android.graphics.Bitmap;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.edu.upb.discoverchat.data.db.base.DbBase;

/**
 * Created by hatsumora on 10/04/15.
 */
public class ImageMessage extends Message {
    Image image;

    public ImageMessage(Bitmap bitmap){
        image = new Image();
        image.setBitmap(bitmap);
    }
    public ImageMessage(Cursor c ){
        image = new Image();
        this.setId(c.getLong(c.getColumnIndex(DbBase.KEY_ID)));
        this.setChat_id(c.getLong(c.getColumnIndex(DbBase.KEY_CHAT_ID)));
        this.setReceiver_id(c.getLong(c.getColumnIndex(DbBase.KEY_RECEIVER_ID)));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            this.setDate(dateFormat.parse(c.getString(c.getColumnIndex(DbBase.FIELD_DATE))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        image.setPath(c.getString(c.getColumnIndex(DbBase.FIELD_PATH_TO_IMAGE)));
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
