package com.optc.optcdbmobile.data.database.filters.sql_syntax;

public class GreaterPredicate extends Predicate {
    public GreaterPredicate(Expression next) {
        super(">", next);
    }
}
