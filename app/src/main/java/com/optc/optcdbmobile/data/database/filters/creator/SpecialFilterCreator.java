package com.optc.optcdbmobile.data.database.filters.creator;

import com.optc.optcdbmobile.data.database.filters.FilterMediator;
import com.optc.optcdbmobile.data.database.filters.FilterType;
import com.optc.optcdbmobile.data.database.filters.FilterUI;

public class SpecialFilterCreator extends FilterCreator {
    public SpecialFilterCreator(FilterMediator mediator) {
        super(mediator);
    }

    @Override
    public FilterUI getHeader() {
        return create(FilterType.SPECIAL);
    }

    @Override
    public FilterUI get(Object... args) {
        String basePattern = "description GLOB '*%s*'";
        String specialPattern = null;

        String label = (String) args[0];
        Object databasePattern = args[1];
        if (databasePattern instanceof String) specialPattern = (String) databasePattern;
        else if (databasePattern instanceof String[]) {
            String[] strings = (String[]) databasePattern;
            StringBuilder builder = new StringBuilder();
            builder.append("(");
            for (int index = 0; index < strings.length; index++) {
                builder.append(String.format(basePattern, strings[index]));
                if (index + 1 != strings.length) {
                    builder.append(" OR ");
                }
            }
            builder.append(")");

            specialPattern = builder.toString();
        }


        return create(FilterType.SPECIAL, label, specialPattern);
    }
}
