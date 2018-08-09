package com.optc.optcdbmobile.data.optcdb.entities.location.parser;

import com.optc.optcdbmobile.data.optcdb.BaseParser;
import com.optc.optcdbmobile.data.optcdb.entities.location.entities.TreasureMapDrops;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TreasureMapDropsParser extends BaseParser<List<TreasureMapDrops>> {
    @Override
    public List<TreasureMapDrops> parse(Object jsParsed) {
        NativeArray array = (NativeArray) jsParsed;
        List<TreasureMapDrops> list = new ArrayList<>();

        for (int index = 0; index < array.size(); index++) {
            NativeObject obj = (NativeObject) array.get(index);

            TreasureMapDrops drops = new TreasureMapDrops();

            drops.setName(toType(obj.get("name"), String.class));
            drops.setThumb(toType(obj.get("thumb"), Integer.class));
            drops.setGlobal(toType(obj.get("global"), Boolean.class));
            drops.setNakama(toType(obj.get("nakama"), Integer.class));
            drops.setGamewith(toType(obj.get("gamewith"), Integer.class));

            for (Map.Entry<Object, Object> e : obj.entrySet()) {
                String key = e.getKey().toString();
                if (!(key.equals("name") ||
                        key.equals("thumb") ||
                        key.equals("global") ||
                        key.equals("nakama") ||
                        key.equals("gamewith"))) {
                    NativeArray seasArray = (NativeArray) e.getValue();
                    List<Integer> idList = new ArrayList<>();
                    for (Object o : seasArray) {
                        idList.add(toType(o, Integer.class));
                    }
                    drops.getSeas().put(key, idList);
                }
            }
            list.add(drops);
        }

        return list;
    }
}
