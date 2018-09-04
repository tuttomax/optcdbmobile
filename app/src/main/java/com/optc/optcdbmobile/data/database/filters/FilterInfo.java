package com.optc.optcdbmobile.data.database.filters;

public class FilterInfo {


    private final int type;
    private FilterType.Subtype subtype;
    private String databasePattern;

    public FilterInfo(int type, FilterType.Subtype subtype) {
        this.type = type;
        this.subtype = subtype;
    }

    public FilterInfo(int type) {
        this(type, null);
    }

    public boolean isHeader() {
        return (type & FilterType.HEADER) == FilterType.HEADER;
    }

    public int getType() {
        return (type & ~FilterType.HEADER);
    }

    public FilterType.Subtype getSubtype() {
        return subtype;
    }

    public String getDatabasePattern() {
        return databasePattern;
    }

    public void setDatabasePattern(String databasePattern) {
        this.databasePattern = databasePattern;
    }
}

