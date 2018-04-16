package mx.edu.utng.lajosefa.Entity;

/**
 * Created by Catherine on 2/27/2018.
 */

public class ReceiveMessage extends Message {

    private Long hour;

    public ReceiveMessage() {
    }

    public ReceiveMessage(Long hour) {
        this.hour = hour;
    }

    public ReceiveMessage(String message, String urlPhoto, String name, String profilePhoto,
                          String typeMessage, Long hour) {
        super(message, urlPhoto, name, profilePhoto, typeMessage);
        this.hour = hour;
    }

    public Long getHour() {
        return hour;
    }

    public void setHour(Long hour) {
        this.hour = hour;
    }
}
