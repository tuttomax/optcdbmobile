package com.optc.optcdbmobile.data.database.filters;

public class Filter {

    private StringBuilder builder;

    private Filter() {
        builder = new StringBuilder();
    }

    public static Filter create() {
        return new Filter();
    }

    public String build() {
        return builder.toString();
    }

    public Filter newCondition(String attr) {
        builder.append(attr).append(" ");
        return this;
    }

    public Filter equals(String right) {
        builder.append("=").append(" ").append(right).append(" ");
        return this;
    }

    public Filter equals() {
        builder.append("=");
        return this;
    }

    public Filter in() {
        builder.append("IN").append(" ");
        return this;
    }

    public Filter open() {
        builder.append("(").append(" ");
        return this;
    }

    public Filter close() {
        builder.append(")").append(" ");
        return this;
    }

    public Filter select(String what) {
        builder.append("SELECT").append(" ").append(what).append(" ");
        return this;
    }

    public Filter from(String from) {
        builder.append("FROM").append(" ").append(from).append(" ");
        return this;
    }

    public Filter where() {
        builder.append("WHERE").append(" ");
        return this;
    }

    public Filter and() {
        builder.append("AND").append(" ");
        return this;
    }

    public Filter or() {
        builder.append("OR").append(" ");
        return this;
    }

    public Filter between(String min, String max) {
        builder.append("BETWEEN").append(" ").append(min).append(" ").append("AND").append(" ").append(max).append(" ");
        return this;
    }

    public Filter not() {
        builder.append("NOT").append(" ");
        return this;
    }

    public Filter exists() {
        builder.append("EXISTS").append(" ");
        return this;
    }

    public Filter greater(String value) {
        builder.append(">").append(" ").append(value).append(" ");
        return this;
    }

    public Filter lesser(String value) {
        builder.append("<").append(" ").append(value).append(" ");
        return this;
    }

    public Filter like(String pattern) {
        builder.append("LIKE").append(" ").append(pattern).append(" ");
        return this;
    }

}
