package com.optc.optcdbmobile.data.database.filters.sql_syntax;

public class ExistsPredicate extends Predicate {
    public ExistsPredicate(GroupStatement next) {
        super("EXISTS", next);
    }
}
