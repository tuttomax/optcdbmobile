package com.optc.optcdbmobile.data.database.filters.sql_syntax;

public class Where extends Clause {
    public Where(String value, Predicate expression) {
        super(String.format("WHERE %s", value), expression);
    }
}
