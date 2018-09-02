package com.optc.optcdbmobile.data.database.filters.creator;

import com.optc.optcdbmobile.data.database.filters.FilterMediator;
import com.optc.optcdbmobile.data.database.filters.FilterType;
import com.optc.optcdbmobile.data.database.filters.FilterUI;

public class ClassFilterCreator extends FilterCreator {
    public ClassFilterCreator(FilterMediator mediator) {
        super(mediator);
    }

    @Override
    public FilterUI getHeader() {
        return create(FilterType.CLASS);
    }

    @Override
    public FilterUI get(Object... args) {
        String stringClass = (String) args[0];
        FilterType.Subtype subtype = (FilterType.Subtype) args[1];
        return create(FilterType.CLASS, subtype, stringClass, stringClass, String.format("%s='%s'",
                subtype == FilterType.Subtype.Class1 ? "class1" : "class2", stringClass));
    }
}
