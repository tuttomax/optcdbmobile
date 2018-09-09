package com.optc.optcdbmobile.data.database.filters.compiler;

import com.optc.optcdbmobile.data.database.filters.FilterUI;

import java.util.Iterator;
import java.util.List;

public class CaptainFilterCompiler extends FilterCompiler {


    public CaptainFilterCompiler(List<FilterUI> list) {
        super(list, CAPTAIN_FLAG);
    }


    @Override
    public String compile() {
        Iterator<FilterUI> captainFilterIterator = list.iterator();
        StringBuilder captainFilter = new StringBuilder();

        captainFilter.append("id IN (SELECT captain_id FROM captain_description_table WHERE ");

        while (captainFilterIterator.hasNext()) {
            FilterUI filterUI = captainFilterIterator.next();
            captainFilter.append(filterUI.getInfo().getDatabasePattern());
            if (captainFilterIterator.hasNext()) captainFilter.append(" AND ");
            else captainFilter.append(")");
        }
        return captainFilter.toString();
    }
}
