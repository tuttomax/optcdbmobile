package com.optc.optcdbmobile.data.database;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.optcdb.API;
import com.optc.optcdbmobile.data.optcdb.Constants;


public class OPTCDatabaseRepository {

    private static OPTCDatabaseRepository instance;

    private static OPTCDatabase database;
    private final SharedPreferences preferences;
    private final Context context;

    public OPTCDatabaseRepository(Context context) {
        this.context = context;
        database = OPTCDatabase.getInstance(context);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static OPTCDatabaseRepository getInstance(Context context) {
        if (instance == null) {
            instance = new OPTCDatabaseRepository(context);
        }
        return instance;
    }


    public void VerifyDatabase() {

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info_wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo info_mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        boolean onlyWifi = preferences.getBoolean(context.getString(R.string.pref_wifi_only_key), true);
        boolean autoDownload = preferences.getBoolean(context.getString(R.string.pref_auto_download_key), false);

        if (((onlyWifi && info_wifi.isConnected()) || (!onlyWifi && info_mobile.isConnected()))) {
            new CheckVersion().execute();
        } else {

        }
    }

    private class CheckVersion extends AsyncTask<Void, Void, String> {

        private String old_version;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            old_version = preferences.getString(context.getString(R.string.pref_database_version_key), "");
            /*if (old_version.equals("")) {
                preferences.edit().putBoolean(context.getString(R.string.pref_database_invalid_key), true).commit();
                throw new RuntimeException("Database version is empty. Invalid state");
            }*/
        }

        @Override
        protected String doInBackground(Void... voids) {
            return (String) API.getData(Constants.VERSION_TYPE);
        }

        @Override
        protected void onPostExecute(String new_version) {
            if (!(old_version.equals(new_version))) {
                preferences.edit().putString(context.getString(R.string.pref_database_version_key), new_version).commit();
            }
        }
    }


}
