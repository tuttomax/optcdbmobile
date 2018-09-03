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


import android.arch.persistence.db.SimpleSQLiteQuery;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.database.entities.Captain;
import com.optc.optcdbmobile.data.database.entities.CaptainDescription;
import com.optc.optcdbmobile.data.database.entities.Evolution;
import com.optc.optcdbmobile.data.database.entities.Limit;
import com.optc.optcdbmobile.data.database.entities.LocationInformation;
import com.optc.optcdbmobile.data.database.entities.Potential;
import com.optc.optcdbmobile.data.database.entities.PotentialDescription;
import com.optc.optcdbmobile.data.database.entities.SailorDescription;
import com.optc.optcdbmobile.data.database.entities.Special;
import com.optc.optcdbmobile.data.database.entities.SpecialDescription;
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

        if (sharedPreferences.getBoolean(Constants.Settings.pref_check_done_key, false)) return;

        if (thereIsConnection(context.getView())) {
            CheckDatabaseVersionAsyncTask task = new CheckDatabaseVersionAsyncTask(new CheckDatabaseVersionAsyncTaskListener(context));
            task.setPreferences(sharedPreferences);
            task.execute();
        }

    }


    public List<Unit> getUnits() {
        return database.unitDAO().getUnits();
    }

    public Unit getUnit(int id) {
        return database.unitDAO().getUnit(id);
    }

    public List<Unit> getUnitsWithName(String name) {
        String query = "%" + name + "%";
        return database.unitDAO().getUnitsWithName(query);
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


    public boolean unitHasEvolutions(int id) {
        int count = database.evolutionDAO().getCountEvolutions(id);
        return count > 0;
    }

    public boolean unitHasEvolvesFrom(int id) {
        int count = database.evolutionDAO().getCountEvolvesFrom(id);
        return count > 0;
    }


    public List<CaptainDescription> getCaptainDescriptions(int id) {
        return database.captainDescriptionDAO().getCaptainDescriptions(id);
    }


    public Captain getCaptain(int id) {
        return database.captainDAO().getCaptain(id);
    }

    public Special getSpecial(int id) {
        return database.specialDAO().getSpecial(id);
    }

    public List<SpecialDescription> getSpecialDescriptions(int id) {
        return database.specialDescriptionDAO().getSpecialDescriptions(id);
    }


    public List<SailorDescription> getSailorDescriptions(int id) {
        return database.sailorDescriptionDAO().getSailorDecriptions(id);
    }

    public List<Potential> getPotentials(int id) {
        return database.potentialDAO().getPotentials(id);
    }

    public List<Limit> getLimits(int id) {
        return database.limitDAO().getLimits(id);
    }

    public List<PotentialDescription> getPotentialDescriptions(int id) {
        return database.potentialDescriptionDAO().getPotentialDescriptions(id);
    }

    public List<Evolution> getEvolvesTo(int id) {
        return database.evolutionDAO().getEvolvesTo(id);
    }

    public List<Evolution> getEvolvesFrom(int id) {
        return database.evolutionDAO().getEvolvesFrom(id);
    }

    public List<LocationInformation> getManualDropsOf(int id) {
        return database.locationDropsDAO().getManualDropsOf(id);
    }

    public List<LocationInformation> getFamilyDropsOf(int id) {
        return database.locationDropsDAO().getFamilyDropsOf(id);
    }

    public Boolean unitHasManuals(int id) {
        int count = database.locationDropsDAO().getManualDropsCount(id);
        return count > 0;
    }

    public Boolean unitHasFamily(int id) {
        int count = database.locationDropsDAO().getFamilyCount(id);
        return count > 0;
    }

    public List<Unit> getUnitsWithFilter(String filter) {
        Log.i("## SQL ##", filter);
        SimpleSQLiteQuery filterQuery = new SimpleSQLiteQuery(filter);

        return database.unitDAO().getUnitsWithFilter(filterQuery);
    }
}
