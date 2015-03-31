package co.edu.upb.discoverchat.data.db.base;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

import co.edu.upb.discoverchat.models.Model;

/**
 * Created by hatsumora on 30/03/15.
 */
public class DbBase extends SQLiteOpenHelper {

    protected Context context;
    protected static final int VERSION = 1;
    protected static final String DATABASE_NAME = "DiscoverChat";

    protected static final String TBL_CHATS = "chats";
    protected static final String TBL_RECEIVERS = "receivers";
    protected static final String TBL_USER = "user";


    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_ROOM_IMAGE_PATH = "room_image_path";
    public static final String KEY_CHAT_ID = "chat_id";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_GOOGLE_CLOUD_MESSAGE = "google_cloud_message";
    public static final String KEY_AUTHENTICATION_TOKEN = "authentication_token";


    public DbBase(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }
    public DbBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
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
        String createReceivers =
                "CREATE TABLE "+ TBL_RECEIVERS +" ( " +
                    KEY_ID+" INTEGER PRIMARY KEY, " +
                    KEY_CHAT_ID+" INTEGER, " +
                    KEY_NAME+" TEXT, " +
                    KEY_PHONE+" TEXT, " +
                    "FOREIGN KEY("+KEY_CHAT_ID+") REFERENCES " +TBL_CHATS+"("+KEY_ID+")"+
                ")";
        String createUser =
                "CREATE TABLE "+TBL_USER+"(" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    KEY_EMAIL + " TEXT, " +
                    KEY_PHONE + " TEXT, " +
                    KEY_GOOGLE_CLOUD_MESSAGE + " TEXT, " +
                    KEY_AUTHENTICATION_TOKEN + " TEXT " +
                ")";

        queryList.add(createChats);
        queryList.add(createReceivers);
        queryList.add(createUser);

        for(String query: queryList)
            db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TBL_CHATS);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_RECEIVERS);

        // Create tables again
        onCreate(db);
    }

}
