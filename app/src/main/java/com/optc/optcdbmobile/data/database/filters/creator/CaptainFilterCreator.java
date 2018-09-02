package com.optc.optcdbmobile.data.database.filters.creator;

import com.optc.optcdbmobile.data.database.filters.FilterMediator;
import com.optc.optcdbmobile.data.database.filters.FilterType;
import com.optc.optcdbmobile.data.database.filters.FilterUI;

public class CaptainFilterCreator extends FilterCreator {
    public CaptainFilterCreator(FilterMediator mediator) {
        super(mediator);
    }

    @Override
    public FilterUI getHeader() {
        return create(FilterType.CAPTAIN);
    }

    @Override
    public FilterUI get(Object... args) {
        String basePattern = "description GLOB '*%s*'";
        String databasePattern = null;

        String captainDesc = (String) args[0];
        String regex = (String) args[1];

        Object obj = args[2];

        if (obj instanceof String) databasePattern = String.format(basePattern, (String) obj);
        else if (obj instanceof String[]) {
            String[] strings = (String[]) obj;
            StringBuilder builder = new StringBuilder();
            builder.append("(");
            for (int index = 0; index < strings.length; index++) {
                builder.append(String.format(basePattern, strings[index]));
                if (index + 1 != strings.length) {
                    builder.append(" OR ");
                }
            }
            builder.append(")");

            databasePattern = builder.toString();
        }
        //return create(FilterType.CAPTAIN, captainDesc, regex, "id IN (SELECT captain_id FROM captain_description_table WHERE " + databasePattern + " )");
        return create(FilterType.CAPTAIN, captainDesc, regex, databasePattern);
    }
}
