package com.optc.optcdbmobile.data.database.filters.sql_syntax;

public class Predicate extends Expression {

    private String operation;
    private Expression next;

    public Predicate(String operation, Expression next) {

        this.operation = operation;
        this.next = next;
    }

    @Override
    public String getContent() {
        return String.format("%s %s ", operation, next.getContent());
    }
}
