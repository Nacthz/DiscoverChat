package co.edu.upb.discoverchat.models;

/**
 * Created by hatsumora on 2/04/15.
 * This is the text incoming and outcoming message model
 */
public class TextMessage extends Message {

    private String content;
    public TextMessage(){

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public Type getType() {
        return Type.TEXT;
    }
}
