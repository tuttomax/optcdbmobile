package com.optc.optcdbmobile.data.database.filters.sql_syntax;

public class NotPredicate extends Predicate {
    public NotPredicate(Expression next) {
        super("NOT", next);
    }
}
