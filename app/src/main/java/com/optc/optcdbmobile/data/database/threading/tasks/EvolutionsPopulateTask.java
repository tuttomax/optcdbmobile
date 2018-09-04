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

package com.optc.optcdbmobile.data.database.threading.tasks;

import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.database.OPTCDatabase;
import com.optc.optcdbmobile.data.database.entities.Evolution;
import com.optc.optcdbmobile.data.database.threading.DatabaseTask;
import com.optc.optcdbmobile.data.database.threading.TaskCallback;

import java.util.List;

public class EvolutionsPopulateTask extends DatabaseTask {
    public EvolutionsPopulateTask(OPTCDatabase database, TaskCallback callback) {
        super(EvolutionsPopulateTask.class.getSimpleName(), database, callback);
    }

    @Override
    public void run() {
        setCurrent(0);
        setState(RUNNING);

        setOperation("Downloading evolution data...");
        try {
            List<Evolution> evolutionList = (List<Evolution>) com.optc.optcdbmobile.data.optcdb.API.getData(Constants.API.EVOLUTIONS_TYPE);

            setOperation("Populating evolution table");
            setMax(evolutionList.size());
            for (Evolution evolution : evolutionList) {
                getDatabase().evolutionDAO().insert(evolution);
                increment();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            setError(ex);
            Thread.currentThread().interrupt();

        }

        setState(COMPLETED);
    }
}
