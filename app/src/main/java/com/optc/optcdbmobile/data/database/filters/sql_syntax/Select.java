package com.optc.optcdbmobile.data.database.filters.sql_syntax;

public class Select extends Clause {
    public Select(GeneralString expression) {
        super("SELECT", expression);
    }
}
