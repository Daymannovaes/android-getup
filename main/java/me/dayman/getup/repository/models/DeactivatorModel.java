package me.dayman.getup.repository.models;

/**
 * Created by Daymannovaes on 23/12/2014.
 */
public class DeactivatorModel {
    private String id;
    private String type; //nfc, image, etc...

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
