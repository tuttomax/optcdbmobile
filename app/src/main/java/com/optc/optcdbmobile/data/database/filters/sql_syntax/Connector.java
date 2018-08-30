package com.optc.optcdbmobile.data.database.filters.sql_syntax;

public abstract class Connector extends Expression {

    protected String keyword;

    public Connector(String keyword) {
        this.keyword = keyword;
    }

    public abstract boolean acceptNext(Expression expression);

    public abstract boolean acceptPrev(Expression expression);
}
