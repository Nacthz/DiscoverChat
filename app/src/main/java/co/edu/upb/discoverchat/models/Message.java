package co.edu.upb.discoverchat.models;

<<<<<<< HEAD
public class Message{

    private String txt;
    private Long id;
    private Boolean itsMine;

    public Message(Long id, String txt, Boolean type) {
        super();
        this.id = id;
        this.txt = txt;
        this.itsMine = type;
    }

    public Boolean itsMine(){
        return itsMine;
    }

    public String getTxt() { return txt; }
    public void setName(String txt) {
        this.txt = txt;
    }

    public Long getId() { return id; }
    public void setId(Long id) {
        this.id = id;
    }

=======
import android.database.Cursor;

import org.json.JSONObject;

/**
 * Created by hatsumora on 2/04/15.
 */
public abstract class Message extends Model{
    public enum Type{
        TEXT,
        IMAGE
    }
    public Message() {
    }

    public Message(JSONObject json) {
        super(json);
    }

    public Message(Cursor c) {
        super(c);
    }

    @Override
    protected void parseJsonObject(JSONObject json) {
        //TODO
    }

    public abstract Type getType();
>>>>>>> origin/master
}