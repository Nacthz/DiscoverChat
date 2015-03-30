package co.edu.upb.discoverchat.data.db;

import android.content.Context;

import java.util.List;

import co.edu.upb.discoverchat.models.Model;

/**
 * Created by hatsumora on 30/03/15.
 */
public class ReceiversManager extends  DbBase implements DbInterface{
    public ReceiversManager(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public long add(Model model) {
        return 0;
    }

    @Override
    public Model get(int id) {
        return null;
    }

    @Override
    public List getAll() {
        return null;
    }

    @Override
    public int getAllCount() {
        return 0;
    }

    @Override
    public int delete(Model model) {
        return 0;
    }
}
