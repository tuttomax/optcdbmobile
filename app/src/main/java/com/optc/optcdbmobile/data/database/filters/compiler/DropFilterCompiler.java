package com.optc.optcdbmobile.data.database.filters.compiler;

import com.optc.optcdbmobile.data.database.filters.FilterUI;

import java.util.Iterator;
import java.util.List;

public class DropFilterCompiler extends FilterCompiler {
    public DropFilterCompiler(List<FilterUI> list) {
        super(list, DROP_FLAG);
    }

    @Override
    public String compile() {
        Iterator<FilterUI> iterator = list.iterator();
        StringBuilder filterString = new StringBuilder();
        filterString.append("id IN ( ");
        while (iterator.hasNext()) {
            filterString.append("SELECT unit_id FROM tag_table WHERE ");
            FilterUI filter = iterator.next();
            filterString.append(filter.getInfo().getDatabasePattern()).append(" ");
            if (iterator.hasNext()) filterString.append(" INTERSECT ");
        }
        filterString.append(")");

        return filterString.toString();
    }
}
