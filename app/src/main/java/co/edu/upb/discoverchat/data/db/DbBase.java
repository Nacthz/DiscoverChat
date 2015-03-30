package co.edu.upb.discoverchat.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hatsumora on 30/03/15.
 */
public class DbBase extends SQLiteOpenHelper {
    protected static final int VERSION = 1;
    protected static final String DATABASE_NAME = "DiscoverChat";

    protected static final String TBL_CHATS = "chats";

    protected static final String KEY_ID = "id";
    protected static final String KEY_NAME = "id";
    protected static final String KEY_ROOM_IMAGE_PATH = "id";



    public DbBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        List<String> queryList = new LinkedList();

        String createChats =
                "CREATE TABLE " + TBL_CHATS +"(" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    KEY_NAME + " TEXT, " +
                    KEY_ROOM_IMAGE_PATH + " TEXT" +
                ")";

        queryList.add(createChats);

        for(String query: queryList)
            db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
