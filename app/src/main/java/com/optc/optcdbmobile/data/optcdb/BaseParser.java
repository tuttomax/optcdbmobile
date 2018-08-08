package com.optc.optcdbmobile.data.optcdb;

public abstract class BaseParser<T> {
    public abstract T parse(Object jsParsed);


    protected <K extends Object> K toType(Object o, Class<K> type) {
        if (o == null) return null;

        else if (o instanceof Double) {
            //Needed beacause javascript number use always 64 floating-point number
            Double d = ((Double) o);
            if (type.getClass().equals(Integer.class)) {
                return type.cast(d.intValue());
            } else if (type.getClass().equals(Float.class)) {
                return type.cast(d.floatValue());
            } else if (type.getClass().equals(Byte.class)) {
                return type.cast(d.byteValue());
            }
        } else return type.cast(o);

        return null;
    }
}