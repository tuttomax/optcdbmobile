package com.optc.optcdbmobile.data.database.filters.sql_syntax;

public class AndConnector extends Connector {

    private Expression expression;

    public AndConnector(Expression expression) {
        super("AND");
        this.expression = expression;
    }

    @Override
    public boolean acceptNext(Expression expression) {
        boolean f1 = expression instanceof Predicate;
        boolean f2 = expression instanceof GroupStatement;
        return (f1 || f2);
    }

    @Override
    public boolean acceptPrev(Expression expression) {
        boolean f1 = expression instanceof Predicate;
        boolean f2 = expression instanceof GroupStatement;
        return (f1 || f2);
    }

    @Override
    public String getContent() {
        return String.format("%s %s ", keyword, expression.getContent());
    }
}
