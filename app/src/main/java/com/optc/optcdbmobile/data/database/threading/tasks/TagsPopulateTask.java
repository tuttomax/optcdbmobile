package com.optc.optcdbmobile.data.database.threading.tasks;

import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.database.OPTCDatabase;
import com.optc.optcdbmobile.data.database.entities.Tag;
import com.optc.optcdbmobile.data.database.threading.DatabaseTask;
import com.optc.optcdbmobile.data.database.threading.TaskCallback;

import java.util.List;

public class TagsPopulateTask extends DatabaseTask {
    public TagsPopulateTask(OPTCDatabase database, TaskCallback callback) {
        super(TagsPopulateTask.class.getSimpleName(), database, callback);
    }

    @Override
    public void run() {
        setCurrent(0);
        setState(RUNNING);
        try {

            setOperation("Downloading tags data...");
            List<Tag> tagsData = (List<Tag>) com.optc.optcdbmobile.data.optcdb.API.getData(Constants.API.TAGS_TYPE);

            setMax(tagsData.size());
            setOperation("Populating tags table");
            for (Tag tag : tagsData) {
                getDatabase().tagDAO().insert(tag);
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

