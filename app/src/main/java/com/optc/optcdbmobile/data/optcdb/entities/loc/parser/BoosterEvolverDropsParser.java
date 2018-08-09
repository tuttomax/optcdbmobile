package com.optc.optcdbmobile.data.optcdb.entities.location.parser;

import com.optc.optcdbmobile.data.optcdb.BaseParser;
import com.optc.optcdbmobile.data.optcdb.Constants;
import com.optc.optcdbmobile.data.optcdb.entities.location.entities.BoosterEvolverDrops;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import java.util.ArrayList;
import java.util.List;

public class BoosterEvolverDropsParser extends BaseParser<List<BoosterEvolverDrops>> {

    @Override
    public List<BoosterEvolverDrops> parse(Object jsParsed) {
        List<BoosterEvolverDrops> list = new ArrayList<>();

        NativeArray array = (NativeArray) jsParsed;
        for (int index = 0; index < array.size(); index++) {
            BoosterEvolverDrops drops = new BoosterEvolverDrops();
            NativeObject obj = (NativeObject) array.get(index);
            drops.setName(toType(obj.get("name"), String.class));
            drops.setDay(toType(obj.get("day"), Byte.class));
            drops.setThumb(toType(obj.get("thumb"), Integer.class));
            drops.setGlobal(toType(obj.get("global"), Boolean.class));
            drops.setNakama(toType(obj.get("nakama"), Integer.class));

            NativeArray generalDrops = toType(obj.get(" "), NativeArray.class);
            NativeArray globalDrops = toType(obj.get("Global"), NativeArray.class);
            NativeArray japanDrops = toType(obj.get("Japan"), NativeArray.class);

            addNativeArray(drops, Constants.GENERAL, generalDrops);
            addNativeArray(drops, Constants.GLOBAL, globalDrops);
            addNativeArray(drops, Constants.JAPAN, japanDrops);

            list.add(drops);
        }


        return list;
    }

    private void addNativeArray(BoosterEvolverDrops drops, byte type, NativeArray array) {

        if (array != null && array.size() > 0) {
            List<Integer> list = new ArrayList<>();
            for (int index = 0; index < array.size(); index++) {
                list.add(toType(array.get(index), Integer.class));
            }
            drops.getDrops().put(type, list);
        }

    }
}
