package com.optc.optcdbmobile.data.database.filters.compiler;

import com.optc.optcdbmobile.data.database.filters.FilterUI;

import java.util.Iterator;
import java.util.List;

public class SpecialFilterCompiler extends FilterCompiler {


    public SpecialFilterCompiler(List<FilterUI> list) {
        super(list, FilterCompiler.SPECIAL_FLAG);
    }

    @Override
    public String compile() {
        Iterator<FilterUI> specialFilterIterator = list.iterator();
        StringBuilder specialFilter = new StringBuilder();
        specialFilter.append("id IN (SELECT special_id FROM special_description_table WHERE ");
        while (specialFilterIterator.hasNext()) {
            FilterUI filterUI = specialFilterIterator.next();
            specialFilter.append(filterUI.getInfo().getDatabasePattern());
            if (specialFilterIterator.hasNext()) specialFilter.append(" AND ");
            else specialFilter.append(")");
        }
        return specialFilter.toString();
    }
}
