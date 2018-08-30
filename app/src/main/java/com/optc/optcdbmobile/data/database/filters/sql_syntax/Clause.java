package com.optc.optcdbmobile.data.database.filters.sql_syntax;

public class Clause extends Expression {

    private String keyword;
    private Expression expression;

    public Clause(String keyword, Expression expression) {
        this.keyword = keyword;
        this.expression = expression;
    }

    @Override
    public String getContent() {
        return String.format("%s %s ", keyword, expression.getContent());
    }
}
