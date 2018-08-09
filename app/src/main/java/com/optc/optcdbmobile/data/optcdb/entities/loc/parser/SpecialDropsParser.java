package com.optc.optcdbmobile.data.optcdb.entities.location.parser;

import com.optc.optcdbmobile.data.optcdb.BaseParser;
import com.optc.optcdbmobile.data.optcdb.Constants;
import com.optc.optcdbmobile.data.optcdb.entities.location.entities.FortnightDrops;
import com.optc.optcdbmobile.data.optcdb.entities.location.entities.SpecialDrops;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpecialDropsParser extends BaseParser<List<SpecialDrops>> {
    @Override
    public List<SpecialDrops> parse(Object jsParsed) {
        NativeArray array = (NativeArray) jsParsed;

        List<SpecialDrops> list = new ArrayList<>();

        for (int index = 0; index < array.size(); index++) {

            NativeObject obj = (NativeObject) array.get(index);

            SpecialDrops drops = new SpecialDrops();

            drops.setName(toType(obj.get("name"), String.class));
            drops.setThumb(toType(obj.get("thumb"), Integer.class));
            drops.setGlobal(toType(obj.get("global"), Boolean.class));
            drops.setNakama(toType(obj.get("nakama"), Integer.class));
            drops.setGamewith(toType(obj.get("gamewith"), Integer.class));
            drops.setCondition(toType(obj.get("condition"), String.class));
            drops.setChallenge(toType(obj.get("challenge"), String.class));
            drops.setShowManual(toType(obj.get("showManual"), Boolean.class));

            NativeArray challengeDataArray = toType(obj.get("challengeData"), NativeArray.class);
            if (challengeDataArray != null) addChallengeData(drops, challengeDataArray);

            NativeArray allDrops = toType(obj.get("All Difficulties"), NativeArray.class);
            NativeArray masterDrops = toType(obj.get("Master"), NativeArray.class);
            NativeArray expertDrops = toType(obj.get("Expert"), NativeArray.class);
            NativeArray eliteDrops = toType(obj.get("Elite"), NativeArray.class);
            NativeArray veteranDrops = toType(obj.get("Veteran"), NativeArray.class);
            NativeArray rookieDrops = toType(obj.get("Rookie"), NativeArray.class);

            if (allDrops != null)
                addDropsOnDifficulties(drops, allDrops, Constants.ALL_DIFFICULTIES);
            if (masterDrops != null) addDropsOnDifficulties(drops, masterDrops, Constants.MASTER);
            if (expertDrops != null) addDropsOnDifficulties(drops, expertDrops, Constants.EXPERT);
            if (eliteDrops != null) addDropsOnDifficulties(drops, eliteDrops, Constants.ELITE);
            if (veteranDrops != null)
                addDropsOnDifficulties(drops, veteranDrops, Constants.VETERAN);
            if (rookieDrops != null) addDropsOnDifficulties(drops, rookieDrops, Constants.ROOKIE);


            for (Map.Entry<Object, Object> e : obj.entrySet()) {
                if (!SpecialDrops.getExclude().contains(e.getKey().toString())) {

                    String key = e.getKey().toString();
                    NativeArray inArray = (NativeArray) e.getValue();
                    List<Integer> temp = new ArrayList<>();
                    for (int iindex = 0; iindex < inArray.size(); iindex++) {
                        temp.add(toType(inArray.get(iindex), Integer.class));
                    }
                    drops.getData().put(key, temp);
                }
            }

            list.add(drops);
        }

        return list;
    }

    private void addChallengeData(FortnightDrops drops, NativeArray challengeDataArray) {
        for (int index = 0; index < challengeDataArray.size(); index++) {
            NativeArray array = (NativeArray) challengeDataArray.get(index);
            for (int i_index = 0; i_index < array.size(); i_index++) {
                List<String> list = new ArrayList<>();
                list.add((String) array.get(i_index));
                drops.getChallengeData().add(list);
            }
        }
    }

    private void addDropsOnDifficulties(FortnightDrops drops, NativeArray dropsArray, byte type) {
        if (dropsArray != null && dropsArray.size() > 0) {
            List<Integer> list = new ArrayList<>();

            for (int index = 0; index < dropsArray.size(); index++) {
                list.add(toType(dropsArray.get(index), Integer.class));
            }

            drops.getDropsOnDifficulties().put(type, list);
        }
    }
}
