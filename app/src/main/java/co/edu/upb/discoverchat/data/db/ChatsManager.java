package co.edu.upb.discoverchat.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by hatsumora on 30/03/15.
 */
public class ChatsManager extends DbBase {
    public ChatsManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, VERSION);
    }
}
