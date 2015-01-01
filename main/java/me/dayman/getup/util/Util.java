package me.dayman.getup.util;

import android.app.Activity;

/**
 * Created by Daymannovaes on 23/12/2014.
 *
 * This class is used to group all the util methods. Util methods that are
 * already grouped in other classes (like the dialogs builder (nfc and alarm))
 * are just extended in an inner class here, like Dispatcher and DialogBuilder.
 *
 */
public class Util {
    public static Nfc Nfc = new Nfc();

    public static class Dispatcher extends me.dayman.getup.util.Dispatcher {
        public Dispatcher(Activity activity) {
            super(activity);
        }
    }
    public static class DialogBuilder extends me.dayman.getup.util.DialogBuilder {   }
}
