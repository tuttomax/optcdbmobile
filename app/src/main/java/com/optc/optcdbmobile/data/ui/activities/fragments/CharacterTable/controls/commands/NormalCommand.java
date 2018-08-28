package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls.commands;

import com.optc.optcdbmobile.data.database.filters.Filter;
import com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls.Command;
import com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls.FilterContext;

public class NormalCommand implements Command {

    @Override
    public void execute(Filter filter, FilterContext context) {
        if (filter == null) return;
        context.getUiFilters().add(filter);
    }

    @Override
    public void rollback(Filter filter, FilterContext context) {
        if (filter == null) return;

        context.getUiFilters().remove(filter);
    }
}
