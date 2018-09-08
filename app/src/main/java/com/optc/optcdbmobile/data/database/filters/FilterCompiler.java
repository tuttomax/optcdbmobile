package com.optc.optcdbmobile.data.database.filters;

import java.util.List;

//TODO Implement this
public interface FilterCompiler {
    boolean canCompile();

    String compile(List<FilterUI> list);
}
