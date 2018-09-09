package com.optc.optcdbmobile.data.database.filters.compiler;

import com.optc.optcdbmobile.data.database.filters.FilterUI;

import java.util.Iterator;
import java.util.List;

public class Class2FilterCompiler extends FilterCompiler {
    public Class2FilterCompiler(List<FilterUI> list) {
        super(list, CLASS2_FLAG);
    }

    @Override
    public String compile() {
        Iterator<FilterUI> classFilterIterator = list.iterator();
        StringBuilder classes2Filter = new StringBuilder();
        classes2Filter.append("(");
        while (classFilterIterator.hasNext()) {
            FilterUI filterUI = classFilterIterator.next();
            classes2Filter.append(filterUI.getInfo().getDatabasePattern());
            if (classFilterIterator.hasNext()) classes2Filter.append(" OR ");
        }
        classes2Filter.append(")");
        return classes2Filter.toString();
    }
}
