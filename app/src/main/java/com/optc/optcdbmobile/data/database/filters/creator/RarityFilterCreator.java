package com.optc.optcdbmobile.data.database.filters.creator;

import com.optc.optcdbmobile.data.database.filters.FilterMediator;
import com.optc.optcdbmobile.data.database.filters.FilterType;
import com.optc.optcdbmobile.data.database.filters.FilterUI;

import java.util.Locale;

public class RarityFilterCreator extends FilterCreator {
    public RarityFilterCreator(FilterMediator mediator) {
        super(mediator);
    }

    @Override
    public FilterUI getHeader() {
        return create(FilterType.RARITY);
    }

    @Override
    public FilterUI get(Object... args) {
        String label = (String) args[0];
        Float star = (Float) args[1];
        String databasePattern = String.format(Locale.ENGLISH, "stars=%.1f", star);
        return create(FilterType.RARITY, label, databasePattern);
    }
}
