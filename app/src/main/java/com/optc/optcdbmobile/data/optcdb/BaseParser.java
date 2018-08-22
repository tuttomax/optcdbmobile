/*
 * Copyright 2018 alessandro
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.optc.optcdbmobile.data.optcdb;

import java.util.HashMap;

public abstract class BaseParser<T> {
    /* FIXME: Integer.class.equals(type.class) doesn't work so i'm using this */
    private static final byte INTEGER_TYPE = 0;
    private static final byte FLOAT_TYPE = 1;
    private static final byte BYTE_TYPE = 2;
    private static final byte STRING_TYPE = 3;
    private static final byte BOOLEAN_TYPE = 3;

    private static final HashMap<Class<?>, Byte> map = new HashMap<Class<?>, Byte>() {{
        put(Integer.class, INTEGER_TYPE);
        put(Float.class, FLOAT_TYPE);
        put(Byte.class, BYTE_TYPE);
        put(String.class, STRING_TYPE);
        put(Boolean.class, BOOLEAN_TYPE);
    }};

    public abstract T parse(Object jsParsed);


    protected <K> K toType(Object o, Class<K> type) {
        if (o == null) {
            return null;
        } else {
            Byte idType = map.get(type);
            if (o instanceof Double) {
                Double d = ((Double) o);
                if (idType == INTEGER_TYPE) {
                    return type.cast(d.intValue());
                } else if (idType == FLOAT_TYPE) {
                    return type.cast(d.floatValue());
                } else if (idType == BYTE_TYPE) {
                    return type.cast(d.byteValue());
                } else if (idType == BOOLEAN_TYPE) {
                    return d.intValue() == 0 ? (K) Boolean.FALSE : (K) Boolean.TRUE;
                }
            } else if (o instanceof String) {
                String s = (String) o;
                if (idType == INTEGER_TYPE) {
                    s = s.substring(7);                    //HACK Fix for resolving bug caused by Clash! Bellamy
                    return type.cast(Integer.parseInt(s));
                } else {
                    return type.cast(s);
                }
            } else {
                return type.cast(o);
            }
        }
        return null;
    }
}