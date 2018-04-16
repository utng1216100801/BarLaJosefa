package mx.edu.utng.lajosefa.Entity;

import java.util.Map;

/**
 * Created by Catherine on 2/27/2018.
 */

public class SendMessage extends Message {
    private Map hour;

    public SendMessage(Map hour) {
        this.hour = hour;
    }

    public SendMessage() {
    }

    public SendMessage(String message, String name, String profilePhoto, String typeMessage, Map hour) {
        super(message, name, profilePhoto, typeMessage);
        this.hour = hour;
    }

    public SendMessage(String message, String urlPhoto, String name, String profilePhoto,
                       String typeMessage, Map hour) {
        super(message, urlPhoto, name, profilePhoto, typeMessage);
        this.hour = hour;
    }

    public Map getHour() {
        return hour;
    }

    public void setHour(Map hour) {
        this.hour = hour;
    }
}
