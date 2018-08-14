package com.optc.optcdbmobile.data.tasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.optcdb.API;

import java.lang.ref.WeakReference;

public class CheckDatabaseVersionAsyncTask extends AsyncTask<Void, String, Byte> {

    private static byte ACTION_SHOW_UPDATE = 1;
    private static byte ACTION_AUTOMATIC_UPDATE = 2;


    private final WeakReference<View> refView;
    private final SharedPreferences preferences;

    private Integer currentVersion;
    private boolean autoDownload;

    public CheckDatabaseVersionAsyncTask(SharedPreferences preferences, View view) {
        this.preferences = preferences;
        this.refView = new WeakReference<>(view);
    }

    @Override
    protected void onPreExecute() {
        currentVersion = preferences.getInt(Constants.Settings.pref_database_version_key, -1);
        autoDownload = preferences.getBoolean(Constants.Settings.pref_auto_download_key, false);
    }


    @Override
    protected Byte doInBackground(Void... voids) {
        Integer newVersion = (Integer) API.getData(Constants.APIType.VERSION_TYPE);

        boolean updateAvailable = currentVersion < newVersion;

        if (updateAvailable) {
            return autoDownload ? ACTION_AUTOMATIC_UPDATE : ACTION_SHOW_UPDATE;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Byte action) {
        if (action != null) {
            if (!refView.isEnqueued()) {
                if (action == ACTION_SHOW_UPDATE) {
                    Snackbar.make(refView.get(), "New database version available", Snackbar.LENGTH_LONG)
                            .setActionTextColor(refView.get().getResources().getColor(R.color.secondaryLightColor))
                            .setAction("UPDATE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    new BuildDatabaseAsyncTask(refView.get()).execute();
                                }
                            }).show();
                } else if (action == ACTION_AUTOMATIC_UPDATE) {
                    Snackbar.make(refView.get(), "Updating database...", Snackbar.LENGTH_SHORT).show();
                    new BuildDatabaseAsyncTask(refView.get()).execute();
                }
            }
        }
    }
}
