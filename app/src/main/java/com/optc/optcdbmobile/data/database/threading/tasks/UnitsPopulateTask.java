package com.optc.optcdbmobile.data.database.threading.tasks;

import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.database.OPTCDatabase;
import com.optc.optcdbmobile.data.database.entities.Unit;
import com.optc.optcdbmobile.data.database.threading.DatabaseTask;
import com.optc.optcdbmobile.data.database.threading.Task;
import com.optc.optcdbmobile.data.database.threading.TaskCallback;
import com.optc.optcdbmobile.data.optcdb.API;

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
            List<Unit> unitsData = (List<Unit>) API.getData(Constants.APIType.UNITS_TYPE);

            setMax(unitsData.size());
            setOperation("Populating units database");
            for (Unit unit : unitsData) {
                getDatabase().unitDAO().insert(unit);
                increment();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            setState(Task.ERROR);
            return;
        }

        setState(COMPLETED);
    }
}
