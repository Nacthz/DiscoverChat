package co.edu.upb.discoverchat.models;

import android.database.Cursor;

/**
 * Created by hatsumora on 30/03/15.
 */
public interface Model {
    public String toJsonString();
    public Model newFromJsonString(String model);
}
