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

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.optcdb.API;

public class CheckDatabaseVersionAsyncTask extends AsyncTask<Void, String, Byte> {

    private SharedPreferences preferences;

    private Integer currentVersion;
    private boolean autoDownload;

    private AsyncTaskListener<Byte> listener;

    public CheckDatabaseVersionAsyncTask(AsyncTaskListener<Byte> listener) {
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

        if (updateAvailable) {
            return autoDownload
                    ? Constants.DatabaseVersionTask.ACTION_AUTOMATIC_UPDATE
                    : Constants.DatabaseVersionTask.ACTION_SHOW_UPDATE;
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
