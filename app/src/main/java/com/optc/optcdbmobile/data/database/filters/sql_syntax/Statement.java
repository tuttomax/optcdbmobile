package com.optc.optcdbmobile.data.database.filters.sql_syntax;

import java.util.LinkedList;

public class Statement extends Expression {
    protected LinkedList<Expression> expressions;

    protected Statement() {
        expressions = new LinkedList<>();
    }

    public void add(Expression expression) {
        expressions.add(expression);
    }

    public void remove(Expression expression) {
        expressions.remove(expression);
    }

    public Expression getLast() {
        return expressions.getLast();
    }

    @Override
    public String getContent() {
        StringBuilder builder = new StringBuilder();
        for (Expression expression : expressions) {
            builder.append(expression.getContent());
        }

        return builder.toString();
    }
}
