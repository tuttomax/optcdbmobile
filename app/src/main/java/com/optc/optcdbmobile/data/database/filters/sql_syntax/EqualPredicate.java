package com.optc.optcdbmobile.data.database.filters.sql_syntax;

public class EqualPredicate extends Predicate {

    public EqualPredicate(Expression next) {
        super("=", next);
    }
}
