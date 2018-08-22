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
import com.optc.optcdbmobile.data.database.entities.Family;
import com.optc.optcdbmobile.data.database.entities.FamilyUnit;
import com.optc.optcdbmobile.data.database.threading.DatabaseTask;
import com.optc.optcdbmobile.data.database.threading.TaskCallback;
import com.optc.optcdbmobile.data.optcdb.entities.FamilyContainer;

import java.util.List;

public class FamiliesPopulateTask extends DatabaseTask {

    public FamiliesPopulateTask(OPTCDatabase database, TaskCallback callback) {
        super(FamiliesPopulateTask.class.getSimpleName(), database, callback);
    }

    @Override
    public void run() {
        setCurrent(0);
        setState(RUNNING);

        try {
            setOperation("Downloading family data...");
            List<FamilyContainer> familyList = (List<FamilyContainer>) com.optc.optcdbmobile.data.optcdb.API.getData(Constants.API.FAMILIES_TYPE);

            setOperation("Populating family table");
            setMax(familyList.size());
            for (int index = 0; index < familyList.size(); index++) {

                FamilyContainer familyContainer = familyList.get(index);
                Family family = familyContainer.getFamily();
                List<Integer> listIds = familyContainer.getUnitIds();

                getDatabase().familyDAO().insert(family);

                for (int familyIndex = 0; familyIndex < listIds.size(); familyIndex++) {
                    getDatabase().familyUnitDAO().insert(new FamilyUnit(family.getId(), listIds.get(familyIndex)));
                }
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
