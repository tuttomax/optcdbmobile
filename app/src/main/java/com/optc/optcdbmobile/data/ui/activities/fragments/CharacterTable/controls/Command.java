package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls;

import com.optc.optcdbmobile.data.database.filters.FilterContext;
import com.optc.optcdbmobile.data.database.filters.FilterInfo;

public interface Command {
    void execute(FilterInfo filter, FilterContext context);

    void rollback(FilterInfo filter, FilterContext context);
}
