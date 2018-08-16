package com.optc.optcdbmobile.data.database.threading;

import com.optc.optcdbmobile.data.database.OPTCDatabase;

public abstract class DatabaseTask extends Task {

    private OPTCDatabase database;

    public DatabaseTask(String operation, OPTCDatabase database, TaskCallback callback) {
        super(operation, callback);
        this.database = database;
    }

    protected OPTCDatabase getDatabase() {
        return database;
    }


}
