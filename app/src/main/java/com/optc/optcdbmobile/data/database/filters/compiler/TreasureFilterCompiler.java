package com.optc.optcdbmobile.data.database.filters.compiler;

import com.optc.optcdbmobile.data.database.filters.FilterUI;

import java.util.Iterator;
import java.util.List;

public class TreasureFilterCompiler extends FilterCompiler {
    public TreasureFilterCompiler(List<FilterUI> list) {
        super(list, TREASURE_FLAG);
    }

    @Override
    public String compile() {
        Iterator<FilterUI> treasureFilterIterator = list.iterator();
        StringBuilder treasureFilter = new StringBuilder();
        while (treasureFilterIterator.hasNext()) {
            FilterUI filterUI = treasureFilterIterator.next();
            treasureFilter.append(filterUI.getInfo().getDatabasePattern());
            //nothing to add
        }

        return treasureFilter.toString();
    }
}
