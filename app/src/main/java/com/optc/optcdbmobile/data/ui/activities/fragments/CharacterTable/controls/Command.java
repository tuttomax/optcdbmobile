package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls;

import com.optc.optcdbmobile.data.database.filters.__FilterContext;
import com.optc.optcdbmobile.data.database.filters.__FilterInfo;

public interface Command {
    void execute(__FilterInfo filter, __FilterContext context);

    void rollback(__FilterInfo filter, __FilterContext context);
}
