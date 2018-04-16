package mx.edu.utng.lajosefa.Entity;

/**
 * Created by Diana Manzano on 29/03/2018.
 */

public class Notification {
    public String body;
    public String title;

    public Notification(String s, String s1) {
        this.title = s;
        this.body = s1;
    }

    public Notification() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
