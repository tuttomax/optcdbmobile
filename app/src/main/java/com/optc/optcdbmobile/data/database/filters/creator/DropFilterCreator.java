package com.optc.optcdbmobile.data.database.filters.creator;

import com.optc.optcdbmobile.data.database.filters.FilterMediator;
import com.optc.optcdbmobile.data.database.filters.FilterType;
import com.optc.optcdbmobile.data.database.filters.FilterUI;

public class DropFilterCreator extends FilterCreator {
    public DropFilterCreator(FilterMediator mediator) {
        super(mediator);
    }

    @Override
    public FilterUI getHeader() {
        return create(FilterType.DROP);
    }

    @Override
    public FilterUI get(Object... args) {
        String label = (String) args[0];
        FilterType.Subtype subtype = (FilterType.Subtype) args[1];
        String pattern = (String) args[2];
        return create(FilterType.DROP, subtype, label, pattern);

    }
}
