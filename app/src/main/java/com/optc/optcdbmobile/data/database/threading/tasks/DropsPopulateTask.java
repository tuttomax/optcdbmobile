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
import com.optc.optcdbmobile.data.optcdb.API;

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

            setOperation("Downloading drops data");
            List<Object> dropsList = (List<Object>) API.getData(Constants.APIType.DROPS_TYPE);

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
            setState(ERROR);
            return;
        }

        setState(COMPLETED);
    }
}
