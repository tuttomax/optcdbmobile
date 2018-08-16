package com.optc.optcdbmobile.data.database;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.tasks.CheckDatabaseVersionAsyncTask;
import com.optc.optcdbmobile.data.tasks.ParallelBuildingDatabaseAsyncTask;


public class OPTCDatabaseRepository {
    /* SINGLETON */
    private static OPTCDatabaseRepository INSTANCE;

    public static OPTCDatabaseRepository getInstance(Application application) {
        if (INSTANCE == null) {
            INSTANCE = new OPTCDatabaseRepository(application);
        }
        return INSTANCE;
    }

    private final OPTCDatabase database;

    private final ConnectivityManager connectivityManager;
    private final SharedPreferences sharedPreferences;

    public OPTCDatabaseRepository(Application application) {
        connectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        database = OPTCDatabase.getInstance(application);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application);
    }


    private boolean thereIsConnection(View view) {
        boolean connected = connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
        if (!connected) {
            Snackbar.make(view, "Can't check for new version", Snackbar.LENGTH_SHORT)
                    .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            super.onDismissed(transientBottomBar, event);

                            Snackbar.make(transientBottomBar.getView(), "No valid network available", Snackbar.LENGTH_LONG)
                                    .setActionTextColor(Color.WHITE)
                                    .setAction("CHECK", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            view.getContext().startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                                        }
                                    }).show();
                        }

                    }).show();

        }
        return connected;
    }

    private boolean thereIsValidConnection(View view) {
        if (thereIsConnection(view)) {
            boolean isWifi = connectivityManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
            boolean isMobile = connectivityManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_MOBILE;

            boolean onlyWifi = sharedPreferences.getBoolean(Constants.Settings.pref_wifi_download_key, true);

            return (isWifi && onlyWifi) || (isMobile && !onlyWifi);
        }

        return false;
    }


    public void BuildDatabase(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (thereIsValidConnection(view)) {
            //new BuildDatabaseAsyncTask(view).execute();
            new ParallelBuildingDatabaseAsyncTask(activity).execute();
            new CheckDatabaseVersionAsyncTask(activity).execute();
        }
    }

    public void CheckVersion(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (thereIsConnection(view)) {
            new CheckDatabaseVersionAsyncTask(activity).execute();
        }
    }

}
