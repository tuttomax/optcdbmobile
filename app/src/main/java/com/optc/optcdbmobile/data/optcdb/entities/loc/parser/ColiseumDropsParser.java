package com.optc.optcdbmobile.data.optcdb.entities.location.parser;

import com.optc.optcdbmobile.data.optcdb.BaseParser;
import com.optc.optcdbmobile.data.optcdb.Constants;
import com.optc.optcdbmobile.data.optcdb.entities.location.entities.ColiseumDrops;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import java.util.ArrayList;
import java.util.List;

public class ColiseumDropsParser extends BaseParser<List<ColiseumDrops>> {
    @Override
    public List<ColiseumDrops> parse(Object jsParsed) {

        NativeArray array = (NativeArray) jsParsed;
        if (array.size() > 1)
            throw new RuntimeException(String.format("Invalid size:%d. Should be 1", array.size()));
        List<ColiseumDrops> list = new ArrayList<>();

        NativeObject obj = (NativeObject) array.get(0);
        ColiseumDrops drops = new ColiseumDrops();
        drops.setName(toType(obj.get("name"), String.class));
        drops.setThumb(toType(obj.get("thumb"), Integer.class));
        drops.setGlobal(toType(obj.get("global"), Boolean.class));
        drops.setGamewith(toType(obj.get("gamewith"), Integer.class));
        drops.setSlefty(toType(obj.get("slefty"), String.class));

        List<Integer> temp = new ArrayList<>();

        NativeArray exhibitionArray = toType(obj.get("Exhibition"), NativeArray.class);
        NativeArray undergroundArray = toType(obj.get("Underground"), NativeArray.class);
        NativeArray chaosArray = toType(obj.get("Chaos"), NativeArray.class);
        NativeArray neoArray = toType(obj.get("Neo"), NativeArray.class);

        for (Object o : exhibitionArray) {
            Integer id = toType(o, Integer.class);
            if (id != null) temp.add(id);
        }
        drops.getALL().put(Constants.EXHIBITION, temp);
        temp.clear();

        for (Object o : undergroundArray) {
            Integer id = toType(o, Integer.class);
            if (id != null) temp.add(id);
        }
        drops.getALL().put(Constants.UNDERGROUND, temp);
        temp.clear();

        for (Object o : chaosArray) {
            Integer id = toType(o, Integer.class);
            if (id != null) temp.add(id);
        }
        drops.getALL().put(Constants.CHAOS, temp);
        temp.clear();

        for (Object o : neoArray) {
            Integer id = toType(o, Integer.class);
            if (id != null) temp.add(id);
        }
        drops.getALL().put(Constants.NEO, temp);

        list.add(drops);
        return list;
    }
}
