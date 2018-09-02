package com.optc.optcdbmobile.data.database.filters.creator;

import com.optc.optcdbmobile.data.database.filters.FilterType;
import com.optc.optcdbmobile.data.database.filters.FilterUI;

public interface FilterFactory {

    FilterUI create(int type, FilterType.Subtype subtype, String label, String regex, String databasePattern);

    FilterUI create(int type, String label, String regex, String databasePattern);

    FilterUI create(int type);
}
