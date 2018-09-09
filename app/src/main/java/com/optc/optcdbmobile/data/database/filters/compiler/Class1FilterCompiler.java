package com.optc.optcdbmobile.data.database.filters.compiler;

import com.optc.optcdbmobile.data.database.filters.FilterUI;

import java.util.Iterator;
import java.util.List;

public class Class1FilterCompiler extends FilterCompiler {
    public Class1FilterCompiler(List<FilterUI> list) {
        super(list, CLASS1_FLAG);
    }

    @Override
    public String compile() {
        Iterator<FilterUI> classFilterIterator = list.iterator();
        StringBuilder classes1Filter = new StringBuilder();
        classes1Filter.append("(");
        while (classFilterIterator.hasNext()) {
            FilterUI filterUI = classFilterIterator.next();
            classes1Filter.append(filterUI.getInfo().getDatabasePattern());
            if (classFilterIterator.hasNext()) classes1Filter.append(" OR ");
        }
        classes1Filter.append(")");
        return classes1Filter.toString();
    }
}
