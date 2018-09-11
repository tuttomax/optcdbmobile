package com.optc.optcdbmobile.data.database.filters.compiler;

import com.optc.optcdbmobile.data.database.filters.FilterUI;

import java.util.Iterator;
import java.util.List;

public class SailorFilterCompiler extends FilterCompiler {
    public SailorFilterCompiler(List<FilterUI> list) {
        super(list, SAILOR_FLAG);
    }


    @Override
    public String compile() {
        Iterator<FilterUI> limitFilterIterator = list.iterator();
        StringBuilder sailorFilter = new StringBuilder();

        sailorFilter.append("id IN ( ");

        while (limitFilterIterator.hasNext()) {
            FilterUI filter = limitFilterIterator.next();
            sailorFilter.append("SELECT sailor_id FROM sailor_description_table WHERE ");
            sailorFilter.append(filter.getInfo().getDatabasePattern()).append(" ");

            if (limitFilterIterator.hasNext()) {
                sailorFilter.append(" INTERSECT ");
            }
        }

        sailorFilter.append(")");


        return sailorFilter.toString();
    }
}
