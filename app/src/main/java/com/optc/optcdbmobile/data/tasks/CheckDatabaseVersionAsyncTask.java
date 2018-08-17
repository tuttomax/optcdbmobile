package com.optc.optcdbmobile.data.tasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.optcdb.API;

public class CheckDatabaseVersionAsyncTask extends AsyncTask<Void, String, Byte> {

    private SharedPreferences preferences;

    private Integer currentVersion;
    private boolean autoDownload;

    private AsyncTaskListner<Byte> listener;

    public CheckDatabaseVersionAsyncTask(AsyncTaskListner<Byte> listener) {
        this.listener = listener;
    }


    @Override
    protected void onPreExecute() {
        if (listener != null) {
            listener.onPreExecute();
        }

        currentVersion = preferences.getInt(Constants.Settings.pref_database_version_key, -1);
        autoDownload = preferences.getBoolean(Constants.Settings.pref_auto_download_key, false);
    }


    @Override
    protected Byte doInBackground(Void... voids) {
        Integer newVersion = (Integer) API.getData(Constants.APIType.VERSION_TYPE);

        boolean updateAvailable = currentVersion < newVersion;

        Constants.Settings.there_is_update = updateAvailable;

        if (updateAvailable) {
            return autoDownload
                    ? Constants.DatabaseVerionTask.ACTION_AUTOMATIC_UPDATE
                    : Constants.DatabaseVerionTask.ACTION_SHOW_UPDATE;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Byte action) {
        if (listener != null) {
            listener.onPostExecute(action);
        }
    }

    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }
}
