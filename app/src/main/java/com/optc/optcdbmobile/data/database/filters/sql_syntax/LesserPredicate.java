package com.optc.optcdbmobile.data.database.filters.sql_syntax;

public class LesserPredicate extends Predicate {
    public LesserPredicate(Expression next) {
        super("<", next);
    }
}
