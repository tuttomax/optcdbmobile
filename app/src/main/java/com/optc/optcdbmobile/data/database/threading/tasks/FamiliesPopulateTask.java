package com.optc.optcdbmobile.data.database.threading.tasks;

import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.database.OPTCDatabase;
import com.optc.optcdbmobile.data.database.entities.Family;
import com.optc.optcdbmobile.data.database.entities.FamilyUnit;
import com.optc.optcdbmobile.data.database.threading.DatabaseTask;
import com.optc.optcdbmobile.data.database.threading.TaskCallback;
import com.optc.optcdbmobile.data.optcdb.API;
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
            List<FamilyContainer> familyList = (List<FamilyContainer>) API.getData(Constants.APIType.FAMILIES_TYPE);

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
            setState(ERROR);
            ex.printStackTrace();
            return;
        }

        setState(COMPLETED);
    }
}
