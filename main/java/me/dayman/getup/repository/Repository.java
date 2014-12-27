package me.dayman.getup.repository;

import com.codeslap.persistence.SqlAdapter;


/**
 * Created by Daymannovaes on 23/12/2014.
 */
public class Repository {
    private static SqlAdapter adapter;

    private Repository() { }

    public static void setAdapter(SqlAdapter adapter) {
        Repository.adapter = adapter;
    }

    public static SqlAdapter getAdapter() {
        return adapter;
    }

}
