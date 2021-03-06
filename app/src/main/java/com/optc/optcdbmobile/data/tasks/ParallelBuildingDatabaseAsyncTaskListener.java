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

import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.database.OPTCDatabaseRepository;
import com.optc.optcdbmobile.data.database.threading.ListTaskDialog;
import com.optc.optcdbmobile.data.database.threading.TaskDelegate;

public class ParallelBuildingDatabaseAsyncTaskListener implements AsyncTaskListener<Boolean> {
    private AsyncTaskContext context;
    private View view;

    private ListTaskDialog dialog;

    public ParallelBuildingDatabaseAsyncTaskListener(AsyncTaskContext supporter) {
        context = supporter;
        view = context.getView();
        dialog = ListTaskDialog.newInstance("Building database");
    }

    @Override
    public void onPreExecute() {
        PreferenceManager.getDefaultSharedPreferences(context.getContext()).edit()
                .putString(Constants.Settings.pref_database_version_key, "").apply();
        dialog.show(context.getSupportFragmentManager(), ListTaskDialog.TAG);
    }

    @Override
    public void onPostExecute(Boolean noError) {
        dialog.dismiss();
        if (noError != null) {

            if (noError) {
                Snackbar.make(view, "Database building complete", Snackbar.LENGTH_LONG).show();
                PreferenceManager.getDefaultSharedPreferences(context.getContext()).edit().putBoolean(Constants.Settings.pref_check_done_key, true).commit();
                PreferenceManager.getDefaultSharedPreferences(context.getContext()).edit().putBoolean(Constants.Settings.pref_update_available, false).commit();
            } else {
                PreferenceManager.getDefaultSharedPreferences(context.getContext()).edit()
                        .putString(Constants.Settings.pref_database_version_key, "").apply();
                PreferenceManager.getDefaultSharedPreferences(context.getContext()).edit().putBoolean(Constants.Settings.pref_check_done_key, false).commit();
                PreferenceManager.getDefaultSharedPreferences(context.getContext()).edit().putBoolean(Constants.Settings.pref_update_available, true).commit();

                Snackbar.make(view, "Error building database", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .setAction("REDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                OPTCDatabaseRepository.getInstance(view.getContext()).BuildDatabase(context);
                            }
                        }).show();
            }
        }
    }

    public TaskDelegate getDelegate() {
        return dialog.getAdapter();
    }
}
