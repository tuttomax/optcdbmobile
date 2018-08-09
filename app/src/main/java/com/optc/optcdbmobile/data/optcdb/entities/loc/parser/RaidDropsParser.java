package com.optc.optcdbmobile.data.optcdb.entities.location.parser;

import com.optc.optcdbmobile.data.optcdb.BaseParser;
import com.optc.optcdbmobile.data.optcdb.Constants;
import com.optc.optcdbmobile.data.optcdb.entities.location.entities.RaidDrops;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import java.util.ArrayList;
import java.util.List;

public class RaidDropsParser extends BaseParser<List<RaidDrops>> {
    @Override
    public List<RaidDrops> parse(Object jsParsed) {

        NativeArray array = (NativeArray) jsParsed;
        List<RaidDrops> list = new ArrayList<>();
        for (int index = 0; index < array.size(); index++) {
            NativeObject obj = (NativeObject) array.get(index);

            RaidDrops drops = new RaidDrops();
            drops.setName(toType(obj.get("name"), String.class));
            drops.setThumb(toType(obj.get("thumb"), Integer.class));
            drops.setGlobal(toType(obj.get("global"), Boolean.class));
            drops.setNakama(toType(obj.get("nakama"), Integer.class));

            Object gamewith = obj.get("gamewith");
            if (gamewith instanceof String) {
                /*Special case inside Clash!! Bellamy
                /      gamewith = '%20%2053151'
                */
                String sub = ((String) gamewith).substring(7);
                drops.setGamewith(Integer.parseInt(sub));
            } else if (gamewith instanceof Double) {
                drops.setGamewith(toType(gamewith, Integer.class));
            }

            drops.setSlefty(toType(obj.get("slefty"), String.class));
            drops.setCondition(toType(obj.get("condition"), String.class));

            NativeArray master = toType(obj.get("Master"), NativeArray.class);
            NativeArray ultimate = toType(obj.get("Ultimate"), NativeArray.class);
            NativeArray expert = toType(obj.get("Expert"), NativeArray.class);

            List<Integer> temp = new ArrayList<>();

            if (master != null) {
                for (Object o : master) {
                    temp.add(toType(o, Integer.class));
                }
                drops.getAll().put(Constants.MASTER, temp);
                temp.clear();

            }

            if (ultimate != null) {
                for (Object o : ultimate) {
                    temp.add(toType(o, Integer.class));
                }
                drops.getAll().put(Constants.ULTIMATE, temp);
                temp.clear();
            }

            if (expert != null) {
                for (Object o : expert) {
                    temp.add(toType(o, Integer.class));
                }
                drops.getAll().put(Constants.EXPERT, temp);
                temp.clear();
            }

            list.add(drops);
        }
        return list;
    }
}
