package co.edu.upb.discoverchat.models;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.edu.upb.discoverchat.data.db.base.DbBase;

public class Chat implements Model {
    private long id;
    private String name;
    private boolean group = false;
    private String roomImagePath;
    private List<Receiver> receivers;

    public Chat(){
        receivers = new ArrayList<>();
    }

    public Chat(Cursor c) {
        super();
        this.setId(c.getInt(c.getColumnIndex(DbBase.KEY_ID)));
        this.setName(c.getString(c.getColumnIndex(DbBase.KEY_NAME)));
        this.setRoomImagePath(c.getString(c.getColumnIndex(DbBase.KEY_ROOM_IMAGE_PATH)));
    }

    public long getId(){
    	return id;
    }
    public Chat setId(long id){
    	this.id = id;
        return this;
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
    public Chat setReceivers(List receivers) {
        this.receivers = receivers;
        if (receivers.size() > 1)
            group = true;
        return this;
    }

    public boolean isGroup(){
        return group;
    }
    @Override
    public String toJsonString() {
        return null;
    }

    @Override
    public Model newFromJsonString(String jsonChat) {
        return null;
    }
}