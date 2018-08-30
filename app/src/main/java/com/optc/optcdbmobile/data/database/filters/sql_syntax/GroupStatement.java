package com.optc.optcdbmobile.data.database.filters.sql_syntax;

public class GroupStatement extends Statement {

    @Override
    public String getContent() {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        builder.append(super.getContent());
        builder.append(")");

        return builder.toString();
    }
}
