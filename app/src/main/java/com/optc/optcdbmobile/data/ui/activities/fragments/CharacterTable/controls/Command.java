package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls;

public interface Command {
    void execute(FilterInfo filter, FilterContext context);

    void rollback(FilterInfo filter, FilterContext context);
}
