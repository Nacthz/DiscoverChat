package co.edu.upb.discoverchat.data.db.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hatsumora on 30/03/15.
 */
public class DbBase extends SQLiteOpenHelper {

    protected Context context;
    protected static final int VERSION = 1;
    protected static final String DATABASE_NAME = "DiscoverChat";

    /**
     * All the tables presents on the application
     *
     */
    protected static final String TBL_CHATS = "chats";
    protected static final String TBL_RECEIVERS = "receivers";
    protected static final String TBL_USER = "user";
    protected static final String TBL_MESSAGES = "messages";
    protected static final String TBL_MESSAGE_TEXT_DETAIL = "textMessages";
    protected static final String TBL_IMAGE_DETAIL = "images";

    /**
     * This keys are for all tables
     */
    public static final String KEY_ID = "id";
    public static final String KEY_ROOM_IMAGE_PATH = "room_image_path";
    public static final String KEY_CHAT_ID = "chat_id";
    public static final String KEY_RECEIVER_ID = "receiver_id";
    public static final String KEY_MESSAGE_ID = "message_id";

    public static final String FIELD_NAME = "name";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_PHONE = "phone";
    public static final String FIELD_GOOGLE_CLOUD_MESSAGE = "google_cloud_message";
    public static final String KEY_AUTHENTICATION_TOKEN= "authentication_token";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_CONTENT = "content";
    public static final String FIELD_DATE = "date_of";

    @SuppressWarnings("UnusedParameters")
    public DbBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        List<String> queryList = new LinkedList<>();

        String createChats =
                "CREATE TABLE " + TBL_CHATS +"(" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    FIELD_NAME + " TEXT, " +
                    KEY_ROOM_IMAGE_PATH + " TEXT" +
                ")";
        String createReceivers =
                "CREATE TABLE "+ TBL_RECEIVERS +" ( " +
                    KEY_ID+" INTEGER PRIMARY KEY, " +
                    KEY_CHAT_ID+" INTEGER, " +
                        FIELD_NAME +" TEXT, " +
                        FIELD_PHONE +" TEXT, " +
                    "FOREIGN KEY("+KEY_CHAT_ID+") REFERENCES " +TBL_CHATS+"("+KEY_ID+")"+
                ")";
        String createUser =
                "CREATE TABLE "+TBL_USER+"(" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                        FIELD_EMAIL + " TEXT, " +
                        FIELD_PHONE + " TEXT, " +
                        FIELD_GOOGLE_CLOUD_MESSAGE + " TEXT, " +
                    KEY_AUTHENTICATION_TOKEN + " TEXT " +
                ")";
        String createMessages =
                "CREATE TABLE " +TBL_MESSAGES+"(" +
                    KEY_ID+" INTEGER PRIMARY KEY, " +
                    KEY_CHAT_ID+" INTEGER, " +
                    KEY_RECEIVER_ID + " INTEGER, " +
                    FIELD_TYPE + " INTEGER, " +
                    FIELD_DATE+" DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY("+KEY_CHAT_ID+") REFERENCES " +TBL_CHATS+"("+KEY_ID+"), "+
                    "FOREIGN KEY("+KEY_RECEIVER_ID+") REFERENCES " +TBL_RECEIVERS+"("+KEY_ID+")"+
                ")";
        String createTextMessages =
                "CREATE TABLE " + TBL_MESSAGE_TEXT_DETAIL +"(" +
                    KEY_ID+" INTEGER PRIMARY KEY, " +
                    KEY_MESSAGE_ID + " INTEGER PRIMARY KEY, " +
                    FIELD_CONTENT + " TEXT" +
                    "FOREIGN KEY("+KEY_MESSAGE_ID+") REFERENCES " +TBL_MESSAGES+"("+KEY_ID+")"+
                ")";

        queryList.add(createChats);
        queryList.add(createReceivers);
        queryList.add(createUser);
        queryList.add(createMessages);
        queryList.add(createTextMessages);

        for(String query: queryList)
            db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TBL_CHATS);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_RECEIVERS);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_MESSAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_MESSAGE_TEXT_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_IMAGE_DETAIL);

        // Create tables again
        onCreate(db);
    }

}
