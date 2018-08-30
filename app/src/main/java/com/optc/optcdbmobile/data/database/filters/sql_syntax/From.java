package com.optc.optcdbmobile.data.database.filters.sql_syntax;

public class From extends Clause {
    public From(GeneralString expression) {
        super("FROM", expression);
    }
}
