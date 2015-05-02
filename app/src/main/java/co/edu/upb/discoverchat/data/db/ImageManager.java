package co.edu.upb.discoverchat.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import co.edu.upb.discoverchat.data.db.base.DbBase;
import co.edu.upb.discoverchat.models.Image;
import co.edu.upb.discoverchat.models.Model;

/**
 * Created by hatsumora on 10/04/15.
 * This class handles all the information about the images on the app
 */
public class ImageManager extends DbBase {

    public ImageManager(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public String getTable() {
        return TBL_IMAGES;
    }

    @Override
    protected Class getModelClass() {
        return Image.class;
    }

    @Override
    public long add(Model model) {
        Image imagen = (Image)model;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(FIELD_IMAGE_PATH,imagen.getPath());
        values.put(FIELD_LATITUDE,imagen.getLatitude());
        values.put(FIELD_LONGITUDE, imagen.getLongitude());

        long id = db.insert(TBL_IMAGES, null, values);

        imagen.setId(id);
        db.close();

        return id;
    }
    @Override
    public int delete(Model model) {
        return 0;
    }
}
