package com.optc.optcdbmobile.data.database.filters.compiler;

import com.optc.optcdbmobile.data.database.filters.FilterUI;

import java.util.Iterator;
import java.util.List;

public class RarityFilterCompiler extends FilterCompiler {
    public RarityFilterCompiler(List<FilterUI> list) {
        super(list, RARITY_FLAG);
    }

    @Override
    public String compile() {
        Iterator<FilterUI> iterator = list.iterator();
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        while (iterator.hasNext()) {
            FilterUI filterUI = iterator.next();
            builder.append(filterUI.getInfo().getDatabasePattern());
            if (iterator.hasNext()) builder.append(" OR ");
        }
        builder.append(")");
        return builder.toString();
    }
}
