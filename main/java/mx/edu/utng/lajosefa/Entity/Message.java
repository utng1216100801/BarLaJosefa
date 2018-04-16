package mx.edu.utng.lajosefa.Entity;

/**
 * Created by Catherine on 2/26/2018.
 */

public class Message {
    private String message;
    private String urlPhoto;
    private String name;
    private String profilePhoto;
    private String typeMessage;
    //private String hour;

    public Message() {
    }

    public Message(String message, String name, String profilePhoto, String typeMessage) {
        this.message = message;
        this.name = name;
        this.profilePhoto = profilePhoto;
        this.typeMessage = typeMessage;
    }

    public Message(String message, String urlPhoto, String name, String profilePhoto, String typeMessage) {
        this.message = message;
        this.urlPhoto = urlPhoto;
        this.name = name;
        this.profilePhoto = profilePhoto;
        this.typeMessage = typeMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(String typeMessage) {
        this.typeMessage = typeMessage;
    }


    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }
}
