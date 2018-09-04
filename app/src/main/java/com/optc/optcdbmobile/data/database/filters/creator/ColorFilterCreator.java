package com.optc.optcdbmobile.data.database.filters.creator;

import com.optc.optcdbmobile.data.database.filters.FilterMediator;
import com.optc.optcdbmobile.data.database.filters.FilterType;
import com.optc.optcdbmobile.data.database.filters.FilterUI;

public class ColorFilterCreator extends FilterCreator {


    public ColorFilterCreator(FilterMediator mediator) {
        super(mediator);
    }


    @Override
    public FilterUI getHeader() {
        return create(FilterType.COLOR);
    }

    /**
     * @param args args[1] = color string
     */
    @Override
    public FilterUI get(Object... args) {
        int type = FilterType.COLOR;
        String color = (String) args[0];
        return create(type, color,
                String.format("type1='%1$s' OR type2='%1$s'", color));
    }
}
