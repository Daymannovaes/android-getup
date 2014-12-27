package me.dayman.getup.repository.logic;

/**
 * Created by Daymannovaes on 23/12/2014.
 */
public class Deactivator {
    private String id;

    public Deactivator(String nfcId) {
        this.id = nfcId;
    }

    public boolean match(String type, String id) {
        switch(type.toLowerCase()){
            case "nfc":
                return matchNfc(id);
            default:
                return matchNfc(id);
        }
    }

    public boolean matchSelf(String id) {
        return this.id == id;
    }

    private boolean matchNfc(String id) {
        return (matchSelf(id) || MasterDeactivator.match("nfc", id));
    }
}
