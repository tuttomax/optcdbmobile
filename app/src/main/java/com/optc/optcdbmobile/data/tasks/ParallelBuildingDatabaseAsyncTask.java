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

import android.os.AsyncTask;

import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.CrashReporter;
import com.optc.optcdbmobile.data.database.OPTCDatabase;
import com.optc.optcdbmobile.data.database.threading.Task;
import com.optc.optcdbmobile.data.database.threading.TaskCallback;
import com.optc.optcdbmobile.data.database.threading.TaskDelegate;
import com.optc.optcdbmobile.data.database.threading.tasks.AliasPopulateTask;
import com.optc.optcdbmobile.data.database.threading.tasks.CooldownsPopulateTask;
import com.optc.optcdbmobile.data.database.threading.tasks.DetailsPopulatorTask;
import com.optc.optcdbmobile.data.database.threading.tasks.DropsPopulateTask;
import com.optc.optcdbmobile.data.database.threading.tasks.EvolutionsPopulateTask;
import com.optc.optcdbmobile.data.database.threading.tasks.FamiliesPopulateTask;
import com.optc.optcdbmobile.data.database.threading.tasks.TagsPopulateTask;
import com.optc.optcdbmobile.data.database.threading.tasks.UnitsPopulateTask;

public class ParallelBuildingDatabaseAsyncTask extends AsyncTask<Void, Object, Float> {

    private AsyncTaskListener<Float> listener;
    private OPTCDatabase database;
    private TaskDelegate delegate;

    public ParallelBuildingDatabaseAsyncTask(AsyncTaskListener<Float> listener) {
        this.listener = listener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (listener != null) {
            listener.onPreExecute();
        }


    }

    @Override
    protected Float doInBackground(Void... voids) {

        try {
            database.clearAllTables();

            //Task with no FOREIGN KEY constraint
            Task unitsTask = new UnitsPopulateTask(database, (TaskCallback) delegate);
            Task dropsTask = new DropsPopulateTask(database, (TaskCallback) delegate);
            Task detailsTask = new DetailsPopulatorTask(database, (TaskCallback) delegate);

            delegate.addTasks(unitsTask, dropsTask, detailsTask);
            delegate.awaitTermination(80);

            //Task with FOREIGN KEY constraint
            Task evolutionsTask = new EvolutionsPopulateTask(database, (TaskCallback) delegate);
            Task familyTask = new FamiliesPopulateTask(database, (TaskCallback) delegate);
            Task cooldownsTask = new CooldownsPopulateTask(database, (TaskCallback) delegate);
            Task tagsTask = new TagsPopulateTask(database, (TaskCallback) delegate);
            Task aliasTask = new AliasPopulateTask(database, (TaskCallback) delegate);

            delegate.addTasks(evolutionsTask, familyTask, cooldownsTask, tagsTask, aliasTask);
            delegate.awaitTermination(40);

        } catch (Exception ex) {
            CrashReporter.SendEmail(ex);
            return -1f;
        }

        return (Float) com.optc.optcdbmobile.data.optcdb.API.getData(Constants.API.VERSION_TYPE);
    }

    @Override
    protected void onPostExecute(Float returnedValue) {
        if (listener != null) {
            listener.onPostExecute(returnedValue);
        }
    }

    public void setDatabase(OPTCDatabase database) {
        this.database = database;
    }

    public void setDelegate(TaskDelegate delegate) {
        this.delegate = delegate;
    }
}
