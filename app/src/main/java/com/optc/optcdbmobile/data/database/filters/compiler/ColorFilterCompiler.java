package com.optc.optcdbmobile.data.database.filters.compiler;

import com.optc.optcdbmobile.data.database.filters.FilterUI;

import java.util.Iterator;
import java.util.List;

public class ColorFilterCompiler extends FilterCompiler {
    public ColorFilterCompiler(List<FilterUI> list) {
        super(list, COLORS_FLAG);
    }

    @Override
    public String compile() {
        Iterator<FilterUI> colorsFilterIterator = list.iterator();
        StringBuilder colorsFilter = new StringBuilder();
        colorsFilter.append("(");
        while (colorsFilterIterator.hasNext()) {
            FilterUI filter = colorsFilterIterator.next();
            colorsFilter.append(filter.getInfo().getDatabasePattern());
            if (colorsFilterIterator.hasNext()) colorsFilter.append(" OR ");
        }
        colorsFilter.append(")");

        return colorsFilter.toString();
    }
}
