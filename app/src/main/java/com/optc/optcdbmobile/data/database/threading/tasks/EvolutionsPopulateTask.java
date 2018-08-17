package com.optc.optcdbmobile.data.database.threading.tasks;

import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.database.OPTCDatabase;
import com.optc.optcdbmobile.data.database.entities.Evolution;
import com.optc.optcdbmobile.data.database.threading.DatabaseTask;
import com.optc.optcdbmobile.data.database.threading.TaskCallback;
import com.optc.optcdbmobile.data.optcdb.API;

import java.util.List;

public class EvolutionsPopulateTask extends DatabaseTask {
    public EvolutionsPopulateTask(OPTCDatabase database, TaskCallback callback) {
        super(EvolutionsPopulateTask.class.getSimpleName(), database, callback);
    }

    @Override
    public void run() {
        setCurrent(0);
        setState(RUNNING);

        setOperation("Downloading evolution list");
        try {
            List<Evolution> evolutionList = (List<Evolution>) API.getData(Constants.APIType.EVOLUTIONS_TYPE);

            setOperation("Populating evolution table");
            setMax(evolutionList.size());
            for (Evolution evolution : evolutionList) {
                getDatabase().evolutionDAO().insert(evolution);
                increment();
            }
        } catch (Exception ex) {
            setError(ex);

            ex.printStackTrace();
            return;
        }

        setState(COMPLETED);
    }
}
