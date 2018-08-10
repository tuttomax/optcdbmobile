package com.optc.optcdbmobile.data.database;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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


    public boolean VerifyDatabase() {

        NetworkInfo info_wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo info_mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        boolean onlyWifi = sharedPreferences.getBoolean("wifi_download", true);
        boolean autoDownload = sharedPreferences.getBoolean("auto_download", false);

        if (((onlyWifi && info_wifi.isConnected()) || (!onlyWifi && info_mobile.isConnected()))) {
            return true;
        }

        if (autoDownload) {

            return true;
        }

        return false;
    }
}
