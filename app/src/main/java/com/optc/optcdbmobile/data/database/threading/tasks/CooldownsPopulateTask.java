package com.optc.optcdbmobile.data.database.threading.tasks;

import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.database.OPTCDatabase;
import com.optc.optcdbmobile.data.database.threading.DatabaseTask;
import com.optc.optcdbmobile.data.database.threading.TaskCallback;
import com.optc.optcdbmobile.data.optcdb.API;
import com.optc.optcdbmobile.data.optcdb.entities.Cooldown;

import java.util.List;

public class CooldownsPopulateTask extends DatabaseTask {
    public CooldownsPopulateTask(OPTCDatabase database, TaskCallback callback) {
        super(CooldownsPopulateTask.class.getSimpleName(), database, callback);
    }

    @Override
    public void run() {
        setCurrent(0);
        setState(RUNNING);
        try {
            setOperation("Downloading special cooldowns data...");
            List<Cooldown> cooldownList = (List<Cooldown>) API.getData(Constants.APIType.COOLDOWNS_TYPE);

            setOperation("Updating units' special cooldown");
            setMax(cooldownList.size());
            for (Cooldown cooldown : cooldownList) {
                getDatabase().specialDescriptionDAO().updateCooldown(cooldown.getId(), cooldown.getMin(), cooldown.getMax());
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
