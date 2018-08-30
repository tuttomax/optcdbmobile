package com.optc.optcdbmobile.data.database.filters.sql_syntax;

public class NotEqualPredicate extends Predicate {
    public NotEqualPredicate(Expression next) {
        super("!=", next);
    }
}
