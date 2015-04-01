package co.edu.upb.discoverchat.models;

import android.database.Cursor;

import java.util.HashMap;

/**
 * Created by hatsumora on 30/03/15.
 */
public interface Model {
    public String toJsonString();
    public HashMap<String, Object> toHashMap();
    public Model newFromJsonString(String model);
}
