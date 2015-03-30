package co.edu.upb.discoverchat.chat;
import java.util.ArrayList;

import co.edu.upb.discoverchat.models.Model;
import  co.edu.upb.discoverchat.models.Receiver;

public class Chat implements Model {
    private long id;
    private String name;
    private boolean group;
    private String roomImagePath;
    private ArrayList<Receiver> receivers;

    public Chat(){
        receivers = new ArrayList<>();
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
    public Chat setId(String roomImagePath){
    	this.roomImagePath = roomImagePath;
        return this;
    }
    public ArrayList<Receiver> getReceivers(){ return receivers; }
    public Chat setReceivers(ArrayList receivers){
    	this.receivers = receivers;
        return this;
    }

}