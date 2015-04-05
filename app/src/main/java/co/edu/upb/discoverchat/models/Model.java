package co.edu.upb.discoverchat.models;

import android.database.Cursor;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import co.edu.upb.discoverchat.exceptions.NotImplementedMethod;

/**
 * Created by hatsumora on 30/03/15.
 * This is the base for all models
 */
public abstract class Model {
    protected long id = -1;
    String TAG = "MODEL: ";
    public Model(){}
    public Model(JSONObject json){
        if(json.has("id"))
            try {
                this.id = json.getLong("id");
            } catch (JSONException e) {
                Log.e(TAG+Model.class.getSimpleName(),"Error: "+e.getMessage());
                e.printStackTrace();
            }
        parseJsonObject(json);
    }

    public long getId() {
        return id;
    }

    protected abstract void parseJsonObject(JSONObject json);
    public JSONObject toJson() throws JSONException{
        throw new NotImplementedMethod();
    }


    @SuppressWarnings("UnusedParameters")
    public Model(Cursor c){
        throw new NotImplementedMethod();
    }
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

}
