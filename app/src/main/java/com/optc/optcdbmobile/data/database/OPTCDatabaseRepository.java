/*
 * Copyright 2018 alessandro
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.optc.optcdbmobile.data.database;


import android.arch.lifecycle.LiveData;
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
import com.optc.optcdbmobile.data.database.entities.Unit;
import com.optc.optcdbmobile.data.tasks.AsyncTaskContext;
import com.optc.optcdbmobile.data.tasks.CheckDatabaseVersionAsyncTask;
import com.optc.optcdbmobile.data.tasks.CheckDatabaseVersionAsyncTaskListener;
import com.optc.optcdbmobile.data.tasks.ParallelBuildingDatabaseAsyncTask;
import com.optc.optcdbmobile.data.tasks.ParallelBuildingDatabaseAsyncTaskListener;

import java.util.List;


public class OPTCDatabaseRepository {
    /* SINGLETON */
    private static OPTCDatabaseRepository INSTANCE;
    private final OPTCDatabase database;
    private final ConnectivityManager connectivityManager;
    private final SharedPreferences sharedPreferences;

    public OPTCDatabaseRepository(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        database = OPTCDatabase.getInstance(context);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

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

        Snackbar.make(view, "No valid network available", Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.WHITE)
                .setAction("CHECK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.getContext().startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                    }
                }).show();
        return false;
    }

    public void BuildDatabase(AsyncTaskContext context) {
        if (thereIsValidConnection(context.getView())) {
            ParallelBuildingDatabaseAsyncTaskListener listner = new ParallelBuildingDatabaseAsyncTaskListener(context);
            ParallelBuildingDatabaseAsyncTask databaseTask = new ParallelBuildingDatabaseAsyncTask(listner);
            databaseTask.setDatabase(database);
            databaseTask.setDelegate(listner.getDelegate());
            databaseTask.execute();

        }
    }

    public void CheckVersion(AsyncTaskContext context) {
        if (sharedPreferences.getBoolean(Constants.Settings.pref_update_available, true)) {
            if (sharedPreferences.getBoolean(Constants.Settings.pref_check_done_key, false)) return;

            if (thereIsConnection(context.getView())) {
                CheckDatabaseVersionAsyncTask task = new CheckDatabaseVersionAsyncTask(new CheckDatabaseVersionAsyncTaskListener(context));
                task.setPreferences(sharedPreferences);
                task.execute();
            }
        }
    }


    public LiveData<List<Unit>> getUnits() {
        return database.unitDAO().getUnits();
    }


    public boolean unitHasCaptain(int id) {

        int count = database.captainDAO().getCount(id);
        return count > 0;
    }

    public boolean unitHasSpecial(int id) {
        int count = database.specialDAO().getCount(id);
        return count > 0;
    }

    public boolean unitHasSailor(int id) {
        int count = database.sailorDAO().getCount(id);
        return count > 0;
    }

    public boolean unitHasPotential(int id) {
        int count = database.potentialDAO().getCount(id);
        return count > 0;
    }

    public boolean unitHasLimit(int id) {
        int count = database.limitDAO().getCount(id);
        return count > 0;
    }


}
