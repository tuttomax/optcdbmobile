package com.optc.optcdbmobile.data.database.filters.creator;

import com.optc.optcdbmobile.data.database.filters.FilterMediator;
import com.optc.optcdbmobile.data.database.filters.FilterType;
import com.optc.optcdbmobile.data.database.filters.FilterUI;

public class PotentialFilterCreator extends FilterCreator {
    public PotentialFilterCreator(FilterMediator mediator) {
        super(mediator);
    }

    @Override
    public FilterUI getHeader() {
        return create(FilterType.LIMIT);
    }

    @Override
    public FilterUI get(Object... args) {
        String basePattern = "name=\"%s\"";
        String label = (String) args[0];
        String pattern = (String) args[1];
        String databasePattern = String.format(basePattern, pattern);
        return create(FilterType.LIMIT, label, databasePattern);
    }
}
