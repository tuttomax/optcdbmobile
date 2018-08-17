package com.optc.optcdbmobile.data.database;


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
import com.optc.optcdbmobile.data.tasks.AsyncTaskContext;
import com.optc.optcdbmobile.data.tasks.CheckDatabaseVersionAsyncTask;
import com.optc.optcdbmobile.data.tasks.CheckDatabaseVersionAsyncTaskListner;
import com.optc.optcdbmobile.data.tasks.ParallelBuildingDatabaseAsyncTask;
import com.optc.optcdbmobile.data.tasks.ParallelBuildingDatabaseAsyncTaskListner;


public class OPTCDatabaseRepository {
    /* SINGLETON */
    private static OPTCDatabaseRepository INSTANCE;

    public OPTCDatabaseRepository(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        database = OPTCDatabase.getInstance(context);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    private final OPTCDatabase database;

    private final ConnectivityManager connectivityManager;
    private final SharedPreferences sharedPreferences;

    public static OPTCDatabaseRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new OPTCDatabaseRepository(context);
        }
        return INSTANCE;
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

    public void BuildDatabase(AsyncTaskContext context) {
        if (thereIsValidConnection(context.getView())) {
            ParallelBuildingDatabaseAsyncTaskListner listner = new ParallelBuildingDatabaseAsyncTaskListner(context);
            ParallelBuildingDatabaseAsyncTask databaseTask = new ParallelBuildingDatabaseAsyncTask(listner);
            databaseTask.setDatabase(database);
            databaseTask.setDelegate(listner.getDelegate());
            databaseTask.execute();

            CheckDatabaseVersionAsyncTask versionTask = new CheckDatabaseVersionAsyncTask(new CheckDatabaseVersionAsyncTaskListner(context));
            versionTask.setPreferences(sharedPreferences);
            versionTask.execute();
        }
    }

    public void CheckVersion(AsyncTaskContext context) {
        if (thereIsConnection(context.getView())) {
            CheckDatabaseVersionAsyncTask task = new CheckDatabaseVersionAsyncTask(new CheckDatabaseVersionAsyncTaskListner(context));
            task.setPreferences(sharedPreferences);
            task.execute();
        }
    }

}
