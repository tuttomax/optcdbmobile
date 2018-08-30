package com.optc.optcdbmobile.data.database.filters.sql_syntax;

public final class GeneralString extends Expression {
    private String content;

    public GeneralString(String content) {
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }
}
