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
import com.optc.optcdbmobile.data.database.entities.Unit;
import com.optc.optcdbmobile.data.database.threading.DatabaseTask;
import com.optc.optcdbmobile.data.database.threading.TaskCallback;

import java.util.List;

public class UnitsPopulateTask extends DatabaseTask {


    public UnitsPopulateTask(OPTCDatabase database, TaskCallback callback) {
        super(UnitsPopulateTask.class.getSimpleName(), database, callback);
    }

    @Override
    public void run() {
        setCurrent(0);
        setState(RUNNING);
        try {

            setOperation("Downloading units data...");
            List<Unit> unitsData = (List<Unit>) com.optc.optcdbmobile.data.optcdb.API.getData(Constants.API.UNITS_TYPE);

            setMax(unitsData.size());
            setOperation("Populating units table");
            for (Unit unit : unitsData) {
                getDatabase().unitDAO().insert(unit);
                increment();
            }

        } catch (Exception ex) {
            setError(ex);
            ex.printStackTrace();
            Thread.currentThread().interrupt();

        }

        setState(COMPLETED);
    }
}
