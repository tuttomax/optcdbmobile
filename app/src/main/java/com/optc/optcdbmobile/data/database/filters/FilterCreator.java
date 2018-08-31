package com.optc.optcdbmobile.data.database.filters;

public interface FilterCreator {

    FilterUI create();

    FilterUI[] createBatch();

}
