package mx.edu.utng.lajosefa.Entity.uploads;

/**
 * Created by Diana Manzano on 02/04/2018.
 */

public class UploadReserve {
    private String name;
    private String phone;
    private String people;
    private String date;
    private String order;

    private String mKey;

    public UploadReserve() {
    }

    public UploadReserve(String name, String phone, String people, String date, String order) {
        this.name = name;
        this.phone = phone;
        this.people = people;
        this.date = date;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getmKey() {
        return mKey;
    }

    public void setKey(String mKey) {
        this.mKey = mKey;
    }
}
