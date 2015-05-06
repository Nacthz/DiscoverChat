package co.edu.upb.discoverchat.data.db.base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import co.edu.upb.discoverchat.models.Model;

/**
 * Created by hatsumora on 30/03/15.
 * :) i'm fucking good
 */
@SuppressWarnings({"unchecked", "TryFinallyCanBeTryWithResources"})
public abstract class DbBase extends SQLiteOpenHelper implements DbInterface {

    protected Context context;
    protected static final int VERSION = 12;
    protected static final String DATABASE_NAME = "DiscoverChat";
    /**
     * All the tables presents on the application
     *
     */

    protected static final String TBL_CHATS = "chats";
    protected static final String TBL_RECEIVERS = "receivers";
    protected static final String TBL_CHATS_RECEIVERS = "chats_receivers";
    protected static final String TBL_USER = "user";
    protected static final String TBL_MESSAGES = "messages";
    protected static final String TBL_MESSAGE_TEXT_DETAIL = "textMessages";
    protected static final String TBL_MESSAGE_IMAGE_DETAIL = "imageMessages";
    protected static final String VIEW_IMAGE_MESSAGE = "imageMessageView";
    protected static final String TBL_IMAGES = "images";

    public static final String TAG_IMAGES_UPDATE = "images_update";
    /**
     * This keys are for all tables
     */
    public static final String KEY_ID = "id";

    public static final String KEY_ROOM_IMAGE_PATH = "room_image_path";
    public static final String KEY_CHAT_ID = "chat_id";
    public static final String KEY_RECEIVER_ID = "receiver_id";
    public static final String KEY_MESSAGE_ID = "message_id";
    public static final String KEY_IMAGE_ID = "image_id";
    public static final String KEY_SERVER_MODEL_ID  = "server_id";

    public static final String FIELD_NAME = "name";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_PHONE = "phone";
    public static final String FIELD_GOOGLE_CLOUD_MESSAGE = "google_cloud_message";
    public static final String KEY_AUTHENTICATION_TOKEN= "authentication_token";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_SENT = "sent";
    public static final String FIELD_READED = "readed";
    public static final String FIELD_CONTENT = "content";
    public static final String FIELD_DATE = "date_of";
    public static final String FIELD_IMAGE_PATH = "image_path";
    public static final String FIELD_IMAGE_URL = "image_url";
    public static final String FIELD_LONGITUDE = "longitude";
    public static final String FIELD_LATITUDE = "latitude";

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
                    FIELD_READED + " INTEGER DEFAULT 0, " +
                    KEY_ROOM_IMAGE_PATH + " TEXT" +
                ")";
        String createReceivers =
                "CREATE TABLE "+ TBL_RECEIVERS +" ( " +
                    KEY_ID+" INTEGER PRIMARY KEY, " +
                    FIELD_NAME +" TEXT, " +
                    FIELD_PHONE +" TEXT " +
                ")";
        String createChatsReceivers =
                "CREATE TABLE "+TBL_CHATS_RECEIVERS+"(" +
                    KEY_CHAT_ID + " INTEGER," +
                    KEY_RECEIVER_ID + " INTEGER," +
                    "FOREIGN KEY("+KEY_CHAT_ID+") REFERENCES " +TBL_CHATS+"("+KEY_ID+"),"+
                    "FOREIGN KEY("+KEY_RECEIVER_ID+") REFERENCES " +TBL_RECEIVERS+"("+KEY_ID+")"+
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
                    FIELD_SENT + " INTEGER DEFAULT 0, " +
                    FIELD_DATE+" DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY("+KEY_CHAT_ID+") REFERENCES " +TBL_CHATS+"("+KEY_ID+"), "+
                    "FOREIGN KEY("+KEY_RECEIVER_ID+") REFERENCES " +TBL_RECEIVERS+"("+KEY_ID+")"+
                ")";
        String createTextMessages =
                "CREATE TABLE " + TBL_MESSAGE_TEXT_DETAIL +"(" +
                    KEY_MESSAGE_ID + " INTEGER, " +
                    FIELD_CONTENT + " TEXT, " +
                    "FOREIGN KEY("+KEY_MESSAGE_ID+") REFERENCES " +TBL_MESSAGES+"("+KEY_ID+")"+
                ")";
        String createImageMessages =
                "CREATE TABLE "+TBL_MESSAGE_IMAGE_DETAIL+"(" +
                        KEY_MESSAGE_ID+" INTEGER, " +
                        KEY_IMAGE_ID+" INTEGER, " +
                        "FOREIGN KEY("+KEY_MESSAGE_ID+") REFERENCES " +TBL_MESSAGES+"("+KEY_ID+"), "+
                        "FOREIGN KEY("+KEY_IMAGE_ID+") REFERENCES " + TBL_IMAGES +"("+KEY_ID+")"+
                ")";
        String createImages =
                "CREATE TABLE " + TBL_IMAGES +"(" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    KEY_SERVER_MODEL_ID + " INTEGER, " +
                    FIELD_IMAGE_PATH + " TEXT, " +
                    FIELD_IMAGE_URL + " TEXT, " +
                    FIELD_LATITUDE + " TEXT, " +
                    FIELD_LONGITUDE + " TEXT "+
                ")";
        String createImageMessageView = "CREATE VIEW "+VIEW_IMAGE_MESSAGE+" AS " +
                "SELECT " +
                    TBL_MESSAGES+".*, " +
                    TBL_IMAGES+"."+ FIELD_IMAGE_PATH +", "+
                    TBL_IMAGES+"."+KEY_ID + " AS "+ KEY_IMAGE_ID+", "+
                    TBL_IMAGES+"."+ FIELD_IMAGE_URL +", "+
                    TBL_IMAGES+"."+FIELD_LONGITUDE+", "+
                    TBL_IMAGES+"."+FIELD_LATITUDE+
                " FROM "+
                    TBL_MESSAGES +" JOIN "+ TBL_MESSAGE_IMAGE_DETAIL +
                        " ON " + TBL_MESSAGES+"."+KEY_ID +" == "+ TBL_MESSAGE_IMAGE_DETAIL+"."+KEY_MESSAGE_ID+
                " JOIN "+TBL_IMAGES+" ON "+TBL_IMAGES+"."+KEY_ID+" == "+TBL_MESSAGE_IMAGE_DETAIL+"."+
                KEY_IMAGE_ID;

        queryList.add(createChats);
        queryList.add(createReceivers);
        queryList.add(createChatsReceivers);
        queryList.add(createUser);
        queryList.add(createMessages);
        queryList.add(createTextMessages);
        queryList.add(createImageMessages);
        queryList.add(createImages);
        queryList.add(createImageMessageView);

        for(String query: queryList)
            db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TBL_CHATS);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_RECEIVERS);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_CHATS_RECEIVERS);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_MESSAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_MESSAGE_TEXT_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_MESSAGE_IMAGE_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_IMAGES);
        db.execSQL("DROP VIEW IF EXISTS " + VIEW_IMAGE_MESSAGE);
        // Create tables again
        onCreate(db);
    }

    public abstract String getTable();

    protected <T extends Model> List<T> getAll(Class<T> _class){
        List<T> models = new ArrayList<>();
        SQLiteDatabase db =  this.getReadableDatabase();

        Cursor c = getAllCursor(db);
        try {
            if (c.moveToFirst())
                do
                    models.add((T) newInstanceFromCursor(_class, c));
                while (c.moveToNext());
        }finally {
            c.close();
        }
        db.close();
        return models;
    }
    public <T extends Model> List<T> getAll(){
        return getAll(getModelClass());
    }

    protected <T extends Model> List<T> getAllBy(String field,Object query, Class<T> _class){
        List<T> models = new ArrayList<>();
        SQLiteDatabase db =  this.getReadableDatabase();

        Cursor c = db.query(getTable(),null,field +" = ?", new String[]{query.toString()},null,null,null);
        try {
            if (c.moveToFirst())
                do
                    if(!c.isClosed())
                        models.add((T) newInstanceFromCursor(_class, c));
                while (c.moveToNext());
        }finally {
            c.close();
        }
        db.close();
        return models;
    }
    public <T extends Model> List<T> getAllBy(String field,Object query){
        return getAllBy(field,query,getModelClass());
    }


    public int update(HashMap<String, Object> fields, String column, String condition){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for(Map.Entry<String, Object> entry : fields.entrySet())
            values.put(entry.getKey(),entry.getValue().toString());

        return db.update(getTable(), values,column,new String[]{condition});
    }
    protected <T extends Model> T findByField(String field, Object object, Class<T> _class){
    SQLiteDatabase db = this.getReadableDatabase();

    Cursor c = db.query(getTable(),null, field + "= ? ", new String[]{String.valueOf(object)},null,null,null,null);
    try {

        if(c != null){
            c.moveToFirst();
            return (T)newInstanceFromCursor(_class,c);
        }
    }finally {
        assert c != null;
        c.close();
    }
    db.close();
    return null;

    }
    public <T extends Model> T findBy(String field, Object object){
        return (T)findByField(field, object, getModelClass());
    }

    protected <T extends Model> T get(long id, Class<? extends Model> _class) {
        return (T)findByField(KEY_ID, id, _class);
    }

    public <T extends Model> T get(long id){
        return (T)get(id,getModelClass());
    }

    public int getAllCount() {
        return getAll().size();
    }
    private <ModelChild extends Model> ModelChild newInstanceFromCursor(Class<? extends Model> _class, Cursor cursor){
        try {
            return (ModelChild)_class.getDeclaredConstructor(Cursor.class).newInstance(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    protected abstract Class getModelClass();

    protected Cursor getAllCursor(SQLiteDatabase db){
        return db.query(getTable(),null,null,null,null,null,null);
    }
}
