package com.optc.optcdbmobile.data.database.filters.compiler;

import com.optc.optcdbmobile.data.database.filters.FilterUI;

import java.util.List;

public class DropFilterCompiler extends FilterCompiler {
    DropFilterCompiler(List<FilterUI> list) {
        super(list, DROP_FLAG);
    }

    @Override
    public String compile() {
        return null;
    }
}
