package com.optc.optcdbmobile.data.database.filters.creator;

import com.optc.optcdbmobile.data.database.filters.FilterMediator;
import com.optc.optcdbmobile.data.database.filters.FilterType;
import com.optc.optcdbmobile.data.database.filters.FilterUI;

public abstract class FilterCreator implements FilterFactory {

    protected FilterMediator mediator;

    public FilterCreator(FilterMediator mediator) {
        this.mediator = mediator;
    }

    public FilterUI create(int type, FilterType.Subtype subtype, String label, String databasePattern) {
        FilterUI out = new FilterUI(type, subtype, label);
        out.getInfo().setDatabasePattern(databasePattern);
        out.setMediator(mediator);
        return out;
    }

    @Override
    public FilterUI create(int type, String label, String databasePattern) {
        FilterUI out = new FilterUI(type, label);
        out.getInfo().setDatabasePattern(databasePattern);
        out.setMediator(mediator);
        return out;
    }

    @Override
    public FilterUI create(int type) {
        FilterUI out = new FilterUI(type);
        out.setMediator(mediator);
        return out;
    }

    public abstract FilterUI getHeader();

    public abstract FilterUI get(Object... args);
}
