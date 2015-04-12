package co.edu.upb.discoverchat.models;
import android.database.Cursor;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.edu.upb.discoverchat.data.db.base.DbBase;

public class Chat extends Model {
    private String name;
    private boolean group = false;
    private boolean hasNewMessages = false;
    private String roomImagePath;
    private List<Receiver> receivers;

    public Chat(){
        receivers = new ArrayList<>();
    }

    public Chat(Cursor c) {
        super();
        this.setId(c.getInt(c.getColumnIndex(DbBase.KEY_ID)));
        this.setName(c.getString(c.getColumnIndex(DbBase.FIELD_NAME)));
        this.setRoomImagePath(c.getString(c.getColumnIndex(DbBase.KEY_ROOM_IMAGE_PATH)));
        this.setHasNewMessages(c.getInt(c.getColumnIndex(DbBase.FIELD_READED))!=0);
    }

    public String getName(){
    	return name;
    }
    public Chat setName(String name){
    	this.name = name;
        return this;
    }
    public String getRoomImagePath(){
    	return roomImagePath;
    }
    public Chat setRoomImagePath(String roomImagePath){
    	this.roomImagePath = roomImagePath;
        return this;
    }
    public List<Receiver> getReceivers(){ return receivers; }

    public Chat setReceivers(List<Receiver> receivers) {
        this.receivers = receivers;
        if (receivers.size() > 1)
            group = true;
        return this;
    }

    public boolean isHasNewMessages() {
        return hasNewMessages;
    }
    public void setHasNewMessages(boolean hasNewMessages) {
        this.hasNewMessages = hasNewMessages;
    }

    public boolean isGroup(){
        return group;
    }

    @Override
    protected void parseJsonObject(JSONObject json) {

    }
}