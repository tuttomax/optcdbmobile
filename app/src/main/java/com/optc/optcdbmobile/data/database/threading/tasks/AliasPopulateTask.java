package com.optc.optcdbmobile.data.database.threading.tasks;

import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.database.OPTCDatabase;
import com.optc.optcdbmobile.data.database.entities.Alias;
import com.optc.optcdbmobile.data.database.threading.DatabaseTask;
import com.optc.optcdbmobile.data.database.threading.TaskCallback;
import com.optc.optcdbmobile.data.optcdb.API;

import java.util.List;

public class AliasPopulateTask extends DatabaseTask {
    public AliasPopulateTask(OPTCDatabase database, TaskCallback callback) {
        super(AliasPopulateTask.class.getSimpleName(), database, callback);
    }

    @Override
    public void run() {
        setCurrent(0);
        setState(RUNNING);

        try {
            setOperation("Downloading alias data...");
            List<Alias> aliases = (List<Alias>) API.getData(Constants.API.ALIASES_TYPE);

            setOperation("Populating alias table");
            setMax(aliases.size());
            for (Alias alias : aliases) {
                getDatabase().aliasDAO().insert(alias);
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
