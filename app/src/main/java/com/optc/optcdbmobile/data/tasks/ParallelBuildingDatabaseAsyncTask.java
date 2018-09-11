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

public class ParallelBuildingDatabaseAsyncTask extends BaseAsyncTask<Void, Void, Boolean> {

    private OPTCDatabase database;
    private TaskDelegate delegate;


    @Override
    protected Boolean doInBackground(Void... voids) {

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
            return false;
        }

        return true;
    }


    public void setDatabase(OPTCDatabase database) {
        this.database = database;
    }

    public void setDelegate(TaskDelegate delegate) {
        this.delegate = delegate;
    }
}
