package com.optc.optcdbmobile.data.database.filters;

import com.optc.optcdbmobile.data.database.filters.sql_syntax.Statement;

public class FilterInfo {


    private final int type;
    private boolean selected;
    private Statement statement;

    public FilterInfo(int type) {
        this.type = type;
    }

    public boolean isHeader() {
        return (type & FilterType.HEADER) == FilterType.HEADER;
    }

    public int getType() {
        return (type & ~FilterType.HEADER);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void toggle() {
        this.selected = !this.selected;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }
}
