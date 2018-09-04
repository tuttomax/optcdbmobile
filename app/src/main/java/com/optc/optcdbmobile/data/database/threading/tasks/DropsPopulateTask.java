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
import com.optc.optcdbmobile.data.database.entities.BoosterEvolverLocation;
import com.optc.optcdbmobile.data.database.entities.ColiseumLocation;
import com.optc.optcdbmobile.data.database.entities.FortnightLocation;
import com.optc.optcdbmobile.data.database.entities.Location;
import com.optc.optcdbmobile.data.database.entities.LocationChallengeData;
import com.optc.optcdbmobile.data.database.entities.LocationDrops;
import com.optc.optcdbmobile.data.database.entities.RaidLocation;
import com.optc.optcdbmobile.data.database.entities.SpecialLocation;
import com.optc.optcdbmobile.data.database.entities.TrainingForestLocation;
import com.optc.optcdbmobile.data.database.entities.TreasureLocation;
import com.optc.optcdbmobile.data.database.threading.DatabaseTask;
import com.optc.optcdbmobile.data.database.threading.TaskCallback;

import java.util.List;

public class DropsPopulateTask extends DatabaseTask {
    public DropsPopulateTask(OPTCDatabase database, TaskCallback callback) {
        super(DropsPopulateTask.class.getSimpleName(), database, callback);
    }

    @Override
    public void run() {
        setCurrent(0);
        setState(RUNNING);
        try {

            setOperation("Downloading drops data...");
            List<Object> dropsList = (List<Object>) com.optc.optcdbmobile.data.optcdb.API.getData(Constants.API.DROPS_TYPE);

            setOperation("Populating drops table");
            setMax(dropsList.size());
            for (Object obj : dropsList) {
                if (obj instanceof Location) {
                    getDatabase().locationDAO().insert((Location) obj);
                } else if (obj instanceof LocationDrops) {
                    getDatabase().locationDropsDAO().insert((LocationDrops) obj);
                } else if (obj instanceof LocationChallengeData) {
                    getDatabase().locationChallengeDataDAO().insert((LocationChallengeData) obj);
                } else if (obj instanceof BoosterEvolverLocation) {
                    getDatabase().boosterEvolverLocationDAO().insert((BoosterEvolverLocation) obj);
                } else if (obj instanceof ColiseumLocation) {
                    getDatabase().coliseumLocationDAO().insert((ColiseumLocation) obj);
                } else if (obj instanceof FortnightLocation) {
                    getDatabase().fortnightLocationDAO().insert((FortnightLocation) obj);
                } else if (obj instanceof RaidLocation) {
                    getDatabase().raidLocationDAO().insert((RaidLocation) obj);
                } else if (obj instanceof SpecialLocation) {
                    getDatabase().specialLocationDAO().insert((SpecialLocation) obj);
                } else if (obj instanceof TrainingForestLocation) {
                    getDatabase().trainingForestLocationDAO().insert((TrainingForestLocation) obj);
                } else if (obj instanceof TreasureLocation) {
                    getDatabase().treasureLocationDAO().insert((TreasureLocation) obj);
                }
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
