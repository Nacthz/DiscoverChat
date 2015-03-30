package co.edu.upb.discoverchat.data.db;

import android.content.Context;

/**
 * Created by hatsumora on 30/03/15.
 */
public class ReceiversManager extends  DbBase {
    public ReceiversManager(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }


}
