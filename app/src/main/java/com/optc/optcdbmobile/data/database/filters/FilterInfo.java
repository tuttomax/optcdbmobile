package com.optc.optcdbmobile.data.database.filters;

import com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls.Command;

public class FilterInfo {

    private int type;
    private String label;
    private Filter filter;
    private Command command;

    public FilterInfo(int type) {
        this.type = type;
        if (isHeader(type)) label = FilterType.name(getBaseType(type));
    }

    private static boolean isHeader(int type) {
        return (type & FilterType.HEADER) == FilterType.HEADER;
    }

    private static int getBaseType(int type) {
        return (type & ~FilterType.HEADER);
    }

    public boolean isHeader() {
        return isHeader(type);
    }

    public String getLabel() {
        return label;
    }

    public FilterInfo setLabel(String label) {
        this.label = label;
        return this;
    }

    public int getType() {
        return type;
    }

    public Filter getFilter() {
        return filter;
    }

    public FilterInfo setFilter(Filter filter) {
        this.filter = filter;
        return this;
    }

    public Command getCommand() {
        return command;
    }

    public FilterInfo setCommand(Command command) {
        this.command = command;
        return this;
    }


}

