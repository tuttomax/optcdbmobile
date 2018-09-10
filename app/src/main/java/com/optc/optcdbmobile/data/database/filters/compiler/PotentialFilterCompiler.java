package com.optc.optcdbmobile.data.database.filters.compiler;

import com.optc.optcdbmobile.data.database.filters.FilterUI;

import java.util.Iterator;
import java.util.List;

public class PotentialFilterCompiler extends FilterCompiler {
    public PotentialFilterCompiler(List<FilterUI> list) {
        super(list, LIMIT_FLAG);
    }

    @Override
    public String compile() {
        Iterator<FilterUI> limitFilterIterator = list.iterator();
        StringBuilder limitFilter = new StringBuilder();
        StringBuilder nameBuilder = new StringBuilder();

        int index = 0;
        while (limitFilterIterator.hasNext()) {
            FilterUI filterUI = limitFilterIterator.next();
            nameBuilder.append(filterUI.getInfo().getDatabasePattern());
            if (limitFilterIterator.hasNext()) {
                nameBuilder.append(" OR ");
            }
        }

        limitFilter.append("id IN (SELECT id FROM potential_table ")
                .append(String.format("WHERE (%s) ", nameBuilder.toString()))
                .append("GROUP BY id ")
                .append(String.format("HAVING COUNT(id)=%d", list.size()))
                .append(")");

        return limitFilter.toString();
    }
}
