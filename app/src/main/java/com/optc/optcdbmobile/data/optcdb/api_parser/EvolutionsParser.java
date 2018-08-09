package com.optc.optcdbmobile.data.optcdb.api_parser;

import com.optc.optcdbmobile.data.database.entities.Evolution;
import com.optc.optcdbmobile.data.optcdb.BaseParser;
import com.optc.optcdbmobile.data.optcdb.entities.Skull;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EvolutionsParser extends BaseParser<List<Evolution>> {

    @Override
    public List<Evolution> parse(Object jsParsed) {

        List<Evolution> list = new ArrayList<>();

        NativeObject root = (NativeObject) jsParsed;


        for (Map.Entry<Object, Object> entry : root.entrySet()) {
            Integer unitId = toType(entry.getKey(), Integer.class);

            NativeObject internalObj = toType(entry.getValue(), NativeObject.class);

            Object evolutionObj = internalObj.get("evolution");
            Object evolversObj = internalObj.get("evolvers");

            if (evolutionObj instanceof Double) {
                list.add(parseSingleEvolution(unitId, evolutionObj, evolversObj));
            } else if (evolutionObj instanceof NativeArray) {
                NativeArray evolutionArray = (NativeArray) evolutionObj;
                NativeArray evolversArray = (NativeArray) evolversObj;

                for (int index = 0; index < evolutionArray.size(); index++) {
                    Object evolutionInternalObj = evolutionArray.get(index);
                    Object evolversInternalObj = evolversArray.get(index);
                    list.add(parseSingleEvolution(unitId, evolutionInternalObj, evolversInternalObj));
                }

            }

        }

        return list;
    }


    private Evolution parseSingleEvolution(int unitId, Object evolutionObj, Object evolversObj) {
        Integer[] mats = parseEvolvers(toType(evolversObj, NativeArray.class));
        Integer evolutionId = toType(evolutionObj, Integer.class);
        return new Evolution(evolutionId, unitId, mats[0], mats[1], mats[2], mats[3], mats[4]);
    }


    private Integer[] parseEvolvers(NativeArray array) {
        Integer[] list = new Integer[5];

        for (int index = 0; index < array.size(); index++) {
            Object evolverObj = array.get(index);
            Integer evolverId = null;

            if (evolverObj instanceof String) {
                //Skull
                String evolverString = toType(evolverObj, String.class);

                evolverId = Skull.getId(evolverString);
            } else if (evolverObj instanceof Double) {
                evolverId = toType(evolverObj, Integer.class);
            }

            list[index] = evolverId;
        }

        for (int index = array.size(); index < 5; index++) {
            list[index] = null;
        }

        return list;

    }

}
