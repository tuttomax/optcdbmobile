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
import com.optc.optcdbmobile.data.database.threading.DatabaseTask;
import com.optc.optcdbmobile.data.database.threading.TaskCallback;
import com.optc.optcdbmobile.data.optcdb.API;
import com.optc.optcdbmobile.data.optcdb.entities.Detail;

import java.util.List;

public class DetailsPopulatorTask extends DatabaseTask {
    public DetailsPopulatorTask(OPTCDatabase database, TaskCallback callback) {
        super(DetailsPopulatorTask.class.getSimpleName(), database, callback);
    }

    @Override
    public void run() {
        setCurrent(0);
        setState(RUNNING);

        try {
            setOperation("Downloading details data...");
            List<Detail> detailList = (List<Detail>) API.getData(Constants.APIType.DETAILS_TYPE);

            setOperation("Updating units' details");
            setMax(detailList.size());
            for (Detail detail : detailList) {

                getDatabase().captainDAO().insert(detail.getCaptainList());
                getDatabase().captainDescriptionDAO().insert(detail.getCaptainDescriptionList());

                getDatabase().specialDAO().insert(detail.getSpecialList());
                getDatabase().specialDescriptionDAO().insert(detail.getSpecialDescriptionList());

                getDatabase().sailorDAO().insert(detail.getSailorList());
                getDatabase().sailorDescriptionDAO().insert(detail.getSailorDescriptionList());

                getDatabase().limitDAO().insert(detail.getLimitList());

                getDatabase().potentialDAO().insert(detail.getPotentialList());
                getDatabase().potentialDescriptionDAO().insert(detail.getPotentialDescriptionList());

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
