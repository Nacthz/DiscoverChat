package co.edu.upb.discoverchat.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import co.edu.upb.discoverchat.data.db.base.MessageManager;
import co.edu.upb.discoverchat.models.ImageMessage;
import co.edu.upb.discoverchat.models.Model;

/**
 * Created by hatsumora on 10/04/15.
 * This references all the images on the chats as ImageMessages
 */
public class ImageMessagesManager extends MessageManager {

    public ImageMessagesManager (Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public long add(Model model) {

        ImageMessage message = (ImageMessage)model;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CHAT_ID,message.getChat_id());
        values.put(KEY_RECEIVER_ID,message.getReceiver_id());
        values.put(FIELD_TYPE,message.getType().toString());
        values.put(FIELD_SENT,message.isSent());
        long message_id = db.insert(TBL_MESSAGES,null,values);
        values = new ContentValues();
        message.setId(message_id);

        values.put(FIELD_IMAGE_PATH, message.getImage().getPath());
        values.put(FIELD_IMAGE_URL, message.getImage().getUrl());
        values.put(FIELD_LATITUDE, message.getImage().getLatitude());
        values.put(FIELD_LONGITUDE,message.getImage().getLongitude());
        long image_id = db.insert(TBL_IMAGES, null, values);

        values = new ContentValues();
        values.put(KEY_MESSAGE_ID, message_id);
        values.put(KEY_IMAGE_ID, image_id);
        db.insert(TBL_MESSAGE_IMAGE_DETAIL,null,values);

        db.close();
        return message_id;
    }

    @Override
    public String getTable() {
        return VIEW_IMAGE_MESSAGE;
    }

    @Override
    protected Class getModelClass() {
        return ImageMessage.class;
    }

    @Override
    public int delete(Model model) {
        return 0;
    }



    @Override
    public List<ImageMessage> getAllBy(String field, Object query) {
        return super.getAllBy(field, query);
    }
}
