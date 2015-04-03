package co.edu.upb.discoverchat.models;

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

}