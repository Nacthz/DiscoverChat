package co.edu.upb.discoverchat.data.db;

import android.content.Context;

import co.edu.upb.discoverchat.data.db.base.DbBase;
import co.edu.upb.discoverchat.models.Model;

/**
 * Created by hatsumora on 10/04/15.
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
        return null;
    }

    @Override
    public long add(Model model) {
        return 0;
    }

    @Override
    public int delete(Model model) {
        return 0;
    }
}
