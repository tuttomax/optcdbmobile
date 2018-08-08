package com.optc.optcdbmobile.data.optcdb.api_parser;

import android.util.Pair;
import android.util.SparseArray;

import com.optc.optcdbmobile.data.optcdb.BaseParser;

import org.mozilla.javascript.NativeArray;

import java.util.List;

// TODO Re-code this
public class CooldownsParser extends BaseParser<SparseArray<Pair<Byte, Byte>>> {

    @Override
    public SparseArray<Pair<Byte, Byte>> parse(Object jsParsed) {
        SparseArray<Pair<Byte, Byte>> map = new SparseArray<>();
        List<Object> temp = (List<Object>) jsParsed;
        int index = 1;
        for (Object element : temp) {
            if (element == null) {
                index++;
            } else if (element instanceof Double) {
                Double d = (Double) element;
                Byte b = d.byteValue();
                map.put(index++, new Pair<>(b, b));
            } else if (element instanceof NativeArray) {
                NativeArray array = (NativeArray) element;
                Byte min = toType(array.get(0), Byte.class);
                Byte max = toType(array.get(1), Byte.class);
                map.put(index++, new Pair<>(min, max));
            }
        }

        return map;

    }
}
