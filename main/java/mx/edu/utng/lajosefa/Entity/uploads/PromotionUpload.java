package mx.edu.utng.lajosefa.Entity.uploads;

/**
 * Created by Catherine on 3/15/2018.
 */

public class PromotionUpload {
    private String description;
    private String urlImage;
    private String key;


    public PromotionUpload() {
    }

    public PromotionUpload(String description, String urlImage) {
        this.description = description;
        this.urlImage = urlImage;
    }

    public PromotionUpload(String description, String urlImage, String key) {
        this.description = description;
        this.urlImage = urlImage;
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
