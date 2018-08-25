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

package com.optc.optcdbmobile.data.tasks;

import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.database.OPTCDatabaseRepository;

import static com.optc.optcdbmobile.data.Constants.DatabaseVersionTask.ACTION_AUTOMATIC_UPDATE;
import static com.optc.optcdbmobile.data.Constants.DatabaseVersionTask.ACTION_SHOW_UPDATE;
import static com.optc.optcdbmobile.data.Constants.DatabaseVersionTask.NO_ACTION;

public class CheckDatabaseVersionAsyncTaskListener implements AsyncTaskListener<Byte> {

    private AsyncTaskContext context;

    public CheckDatabaseVersionAsyncTaskListener(AsyncTaskContext supporter) {
        context = supporter;
    }

    @Override
    public void onPreExecute() {
        //Snackbar.make(context.getView(), "Checking database update...", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onPostExecute(Byte action) {

        PreferenceManager.getDefaultSharedPreferences(context.getContext()).edit()
                .putBoolean(Constants.Settings.pref_update_available, true)
                .apply();

        switch (action) {
            case ACTION_SHOW_UPDATE:
                Snackbar.make(context.getView(), "New database version available", Snackbar.LENGTH_LONG)
                        .setAction("UPDATE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //new BuildDatabaseAsyncTask(refView.get()).execute();
                                OPTCDatabaseRepository.getInstance(context.getView().getContext()).BuildDatabase(context);
                            }
                        }).show();
                break;
            case ACTION_AUTOMATIC_UPDATE:
                Snackbar.make(context.getView(), "Updating database...", Snackbar.LENGTH_SHORT).show();
                //new BuildDatabaseAsyncTask(refView.get()).execute();
                OPTCDatabaseRepository.getInstance(context.getView().getContext()).BuildDatabase(context);
                break;
            case NO_ACTION:
                PreferenceManager.getDefaultSharedPreferences(context.getContext()).edit()
                        .putBoolean(Constants.Settings.pref_update_available, false)
                        .apply();

                PreferenceManager.getDefaultSharedPreferences(context.getContext()).edit()
                        .putBoolean(Constants.Settings.pref_check_done_key, true)
                        .apply();
                break;
        }
    }
}