package com.optc.optcdbmobile.data.database;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;


public class OPTCDatabaseRepository {

    private final OPTCDatabase database;
    private final ConnectivityManager manager;
    private final SharedPreferences sharedPreferences;

    public OPTCDatabaseRepository(Application application) {
        manager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        database = OPTCDatabase.getInstance(application);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application);
    }



}
