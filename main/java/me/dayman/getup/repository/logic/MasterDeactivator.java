package me.dayman.getup.repository.logic;

import me.dayman.getup.repository.Repository;

/**
 * Created by Daymannovaes on 23/12/2014.
 */
public class MasterDeactivator {
    private static Deactivator deactivator;

    public static void setDeactivator(String nfcId) {
        MasterDeactivator.deactivator = new Deactivator(nfcId);

        Repository.getAdapter().store(deactivator);
    }
    public static void setDeactivator(Deactivator deactivator) {
        MasterDeactivator.deactivator = deactivator;
    }

    public static boolean match(String type, String id) {
        return deactivator.match(type, id);
    }
}
