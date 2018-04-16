package mx.edu.utng.lajosefa.Entity.uploads;

import com.google.firebase.database.Exclude;

/**
 * Created by Catherine on 3/5/2018.
 */

public class ScheduleUpload {
    private String dayName;
    private String openHour;
    private String closeHour;
    private String urlImage;
    private String key;

    public ScheduleUpload(){
        //Empty constructor needed
    }

    public ScheduleUpload(String dayName, String openHour, String closeHour, String urlImage) {
        if(dayName.trim().equals("")){
            dayName = "No especificado";
        }

        this.dayName = dayName;
        this.openHour = openHour;
        this.closeHour = closeHour;
        this.urlImage = urlImage;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getOpenHour() {
        return openHour;
    }

    public void setOpenHour(String openHour) {
        this.openHour = openHour;
    }

    public String getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(String closeHour) {
        this.closeHour = closeHour;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
