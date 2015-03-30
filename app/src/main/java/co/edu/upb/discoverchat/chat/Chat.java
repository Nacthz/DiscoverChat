package co.edu.upb.discoverchat.chat;
import java.util.ArrayList;

import  co.edu.upb.discoverchat.models.Receiver;

public class Chat {
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
    }
    public String getName(){
    	return name;
    }
    public Chat setName(String name){
    	this.name = name;
    }
    public String getRoomImagePath(){
    	return roomImagePath;
    }
    public Chat setId(String roomImagePath){
    	this.roomImagePath = roomImagePath;
    }
    public ArrayList<Receiver> getReceivers(){ return receivers; }
    public Chat setReceivers(ArrayList receivers){
    	this.receivers = receivers;
    }

}