package com.optc.optcdbmobile.data.optcdb.location.parser;

import com.optc.optcdbmobile.data.optcdb.BaseParser;
import com.optc.optcdbmobile.data.optcdb.location.entities.TrainingForestDrops;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class TraningForestDropsParser extends BaseParser<List<TrainingForestDrops>> {
    @Override
    public List<TrainingForestDrops> parse(Object jsParsed) {
        NativeArray array = (NativeArray) jsParsed;
        List<TrainingForestDrops> list = new ArrayList<>();

        for (int index = 0; index < array.size(); index++) {
            NativeObject obj = (NativeObject) array.get(index);
            TrainingForestDrops drops = new TrainingForestDrops();
            drops.setName(toType(obj.get("name"), String.class));
            drops.setThumb(toType(obj.get("thumb"), Integer.class));
            drops.setGamewith(toType(obj.get("gamewith"), Integer.class));
            drops.setSlefty(toType(obj.get("slefty"), String.class));
            drops.setGlobal(toType(obj.get("global"), Boolean.class));
            drops.setNakama(toType(obj.get("nakama"), Integer.class));
            drops.setCompletion(toType(obj.get("completion"), String.class));

            list.add(drops);
        }

        return list;
    }
}
