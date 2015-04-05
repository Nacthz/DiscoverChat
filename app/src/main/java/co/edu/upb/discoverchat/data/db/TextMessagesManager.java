package co.edu.upb.discoverchat.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import co.edu.upb.discoverchat.data.db.base.DbBase;
import co.edu.upb.discoverchat.models.Message;
import co.edu.upb.discoverchat.models.Model;
import co.edu.upb.discoverchat.models.TextMessage;

/**
 * Created by hatsumora on 3/04/15.
 */
public class TextMessagesManager extends DbBase {

    public TextMessagesManager(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public long add(Model model) {
        TextMessage textMessage = (TextMessage)model;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CHAT_ID,textMessage.getChat_id());
        values.put(KEY_RECEIVER_ID,textMessage.getReceiver_id());
        values.put(FIELD_TYPE,textMessage.getType().toString());
        values.put(FIELD_SENT,textMessage.isSent());
        long id = db.insert(TBL_MESSAGES,null,values);
        values = new ContentValues();
        textMessage.setId(id);
        values.put(FIELD_CONTENT, textMessage.getContent());
        values.put(KEY_MESSAGE_ID, textMessage.getId());
        db.insert(TBL_MESSAGE_TEXT_DETAIL, null, values);


        return id;
    }

    @Override
    public List<TextMessage> getAllBy(String field, Object query) {
        return super.getAllBy(field, query);
    }

    @Override
    public int delete(Model model) {
        // TODO
        return 0;
    }

    @Override
    public String getTable() {
        return TBL_MESSAGES+" JOIN "+TBL_MESSAGE_TEXT_DETAIL+" ON "+TBL_MESSAGES+"."+KEY_ID+" == " + TBL_MESSAGE_TEXT_DETAIL+"."+KEY_MESSAGE_ID;
    }

    @Override
    protected Class getModelClass() {
        return TextMessage.class;
    }

    public Collection<? extends Message> getAllNotSend() {
        return getAllBy(FIELD_SENT,false);
    }
}
