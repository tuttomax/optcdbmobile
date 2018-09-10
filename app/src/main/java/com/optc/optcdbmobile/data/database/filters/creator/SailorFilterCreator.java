package com.optc.optcdbmobile.data.database.filters.creator;

import com.optc.optcdbmobile.data.database.filters.FilterMediator;
import com.optc.optcdbmobile.data.database.filters.FilterType;
import com.optc.optcdbmobile.data.database.filters.FilterUI;

public class SailorFilterCreator extends FilterCreator {
    public SailorFilterCreator(FilterMediator mediator) {
        super(mediator);
    }

    @Override
    public FilterUI getHeader() {
        return create(FilterType.SAILOR);
    }

    @Override
    public FilterUI get(Object... args) {
        String label = (String) args[0];
        Object pattern = args[1];
        String basePattern = "description GLOB '*%s*'";

        //String basePattern = "sailor_id IN (SELECT sailor_id WHERE description GLOB '*%s*')";

        String databasePattern = null;
        if (pattern instanceof String) databasePattern = String.format(basePattern, pattern);
        else {
            String[] strings = (String[]) pattern;
            StringBuilder builder = new StringBuilder();
            builder.append("(");
            for (int index = 0; index < strings.length; index++) {
                builder.append(String.format(basePattern, strings[index]));
                if (index + 1 != strings.length) {
                    builder.append(" OR ");
                }
            }
            builder.append(")");
            databasePattern = String.format(basePattern, builder.toString());
        }
        return create(FilterType.SAILOR, label, databasePattern);

    }
}
