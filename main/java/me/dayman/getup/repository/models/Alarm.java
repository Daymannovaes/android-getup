package me.dayman.getup.repository.models;

import me.dayman.getup.repository.logic.AlarmLogic;

/**
 * Created by Daymannovaes on 23/12/2014.
 */
public class Alarm {
    private long id;

    private int hour;
    private int minute;
    private boolean repeat;

    public Alarm() { }

    public Alarm(int hour, int minute, boolean repeat, String deactivatorType) {
        this.hour = hour;
        this.minute = minute;
        this.repeat = repeat;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public String toString() {
        return AlarmLogic.formatZero(hour) + ":" + AlarmLogic.formatZero(minute);
    }
}
