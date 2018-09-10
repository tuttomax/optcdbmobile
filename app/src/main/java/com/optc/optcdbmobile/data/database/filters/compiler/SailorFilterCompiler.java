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
        //StringBuilder conditionBuilder = new StringBuilder();

        sailorFilter.append("id IN ( ");

        while (limitFilterIterator.hasNext()) {
            FilterUI filter = limitFilterIterator.next();
            sailorFilter.append("SELECT sailor_id FROM sailor_description_table WHERE ");
            //conditionBuilder.append(filterUI.getInfo().getDatabasePattern());
            sailorFilter.append(filter.getInfo().getDatabasePattern()).append(" ");

            if (limitFilterIterator.hasNext()) {
                sailorFilter.append(" INTERSECT ");
            }
        }

        sailorFilter.append(")");

        /*
        sailorFilter.append("id IN (SELECT sailor_id FROM sailor_description_table ")
                .append(String.format("WHERE %s ", conditionBuilder.toString()))
                //.append("GROUP BY sailor_id ")
                //.append(String.format("HAVING COUNT(sailor_id)=%d", list.size()))
                .append(")");
        */

        return sailorFilter.toString();
    }
}
