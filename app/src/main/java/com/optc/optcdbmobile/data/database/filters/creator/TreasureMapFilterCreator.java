package com.optc.optcdbmobile.data.database.filters.creator;

import com.optc.optcdbmobile.data.database.filters.FilterMediator;
import com.optc.optcdbmobile.data.database.filters.FilterType;
import com.optc.optcdbmobile.data.database.filters.FilterUI;

public class TreasureMapFilterCreator extends FilterCreator {
    public TreasureMapFilterCreator(FilterMediator mediator) {
        super(mediator);
    }

    @Override
    public FilterUI getHeader() {
        return create(FilterType.TREASURE_MAP);
    }

    @Override
    public FilterUI get(Object... args) {
        String label = (String) args[0];
        String regex = (String) args[1];
        Integer[] unitList = (Integer[]) args[2];
        StringBuilder allIds = new StringBuilder();
        for (int index = 0; index < unitList.length; index++) {
            allIds.append(String.valueOf(unitList[index]));
            if (index + 1 != unitList.length) allIds.append(",");
        }
        return create(FilterType.TREASURE_MAP, label, regex, String.format("id IN ( %s )", allIds.toString()));
    }
}
