package co.edu.upb.discoverchat.models;

public class Receiver {
    private long id;
    private long chat_id;
    private String name;
    private String phone;

    public long getId() {return id;}

    public Receiver setId(long id) {
        this.id = id;
        return this;
    }
    public long getChat_id() {
        return chat_id;
    }
    public Receiver setChat_id(long chat_id) {
        this.chat_id = chat_id;
        return this;
    }
    public String getName() {
        return name;
    }
    public Receiver setName(String name) {
        this.name = name;
        return this;
    }
    public String getPhone() {
        return phone;
    }
    public Receiver setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}