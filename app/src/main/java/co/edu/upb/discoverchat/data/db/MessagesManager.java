package co.edu.upb.discoverchat.data.db;

import android.content.Context;

import java.util.List;

import co.edu.upb.discoverchat.data.db.base.DbBase;
import co.edu.upb.discoverchat.models.Message;
import co.edu.upb.discoverchat.models.Model;

/**
 * Created by hatsumora on 3/04/15.
 */
public class MessagesManager extends DbBase {

    public MessagesManager(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public long add(Model model) {
        return 0;
    }

    @Override
    public int delete(Model model) {
        // TODO
        return 0;
    }

    @Override
    public String getTable() {
        return TBL_MESSAGES;
    }

    @Override
    protected Class getModelClass() {
        return Message.class;
    }
}
