package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls;

import com.optc.optcdbmobile.data.database.filters.Filter;

public interface Command {
    void execute(Filter filter, FilterContext context);

    void rollback(Filter filter, FilterContext context);
}
