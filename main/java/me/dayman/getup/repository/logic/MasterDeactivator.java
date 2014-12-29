package me.dayman.getup.repository.logic;

import me.dayman.getup.repository.Repository;

/**
 * Created by Daymannovaes on 23/12/2014.
 */
public class MasterDeactivator {
    private static DeactivatorLogic deactivator;

    public static void setDeactivator(String nfcId) {
        MasterDeactivator.deactivator = new DeactivatorLogic(nfcId);

        //@todo
        //Repository.getAdapter().store(deactivator);
    }
    public static void setDeactivator(DeactivatorLogic deactivator) {
        MasterDeactivator.deactivator = deactivator;
    }

    public static boolean match(String id) {
        return deactivator.matchSelf(id);
    }
}
