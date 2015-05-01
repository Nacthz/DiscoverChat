package co.edu.upb.discoverchat.exceptions;

/**
 * Created by hatsumora on 1/05/15.
 */
public class NotCalleableMethod extends RuntimeException {
    @Override
    public String getMessage() {
        return "This method can't be called" +
                "\n"+super.getMessage();
    }
}
