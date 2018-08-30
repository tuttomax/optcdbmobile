package com.optc.optcdbmobile.data.database.filters.sql_syntax;

public class InPredicate extends Predicate {
    public InPredicate(GroupStatement next) {
        super("IN", next);
    }
}
