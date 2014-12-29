package me.dayman.getup.util;

/**
 * Created by Daymannovaes on 23/12/2014.
 *
 * This class is used to group all the util methods that don't need to
 * have parameters in the instantiation, so they can be accessed statically.
 * Utils like the Dispatcher can't be used here (its needs the activity instance
 * and a other default params). The static properties here have a protected constructor.
 *
 */
public class Util {
    public static Nfc Nfc = new Nfc();
}
