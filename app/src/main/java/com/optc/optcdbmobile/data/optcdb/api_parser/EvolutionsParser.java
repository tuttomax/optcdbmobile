package com.optc.optcdbmobile.data.optcdb.api_parser;

import com.optc.optcdbmobile.data.database.entities.Evolution;
import com.optc.optcdbmobile.data.optcdb.BaseParser;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//TODO Implement this
public class EvolutionsParser extends BaseParser<List<Evolution>> {
    /*
      21: {
            evolution: [ 22, 23, 24, 25, 26 ],
            evolvers: [ [ 116,"adsfadsf",81, 98 ], [ 116, 82, 99 ], [ 116, 78, 95 ], [ 116, 79, 96 ], [ 116, 80, 97 ] ]

     */
    @Override
    public List<Evolution> parse(Object jsParsed) {
        List<Evolution> list = new ArrayList<>();

        NativeObject root = (NativeObject) jsParsed;

        for (Map.Entry<Object, Object> entry : root.entrySet()) {
            Integer unitId = toType(entry.getKey(), Integer.class);

            NativeObject internalObj = toType(entry.getValue(), NativeObject.class);

            NativeArray evolutionsId = toType(internalObj.get("evolution"), NativeArray.class);
            NativeArray allEvolvers = toType(internalObj.get("evolution"), NativeArray.class);

            if (evolutionsId.size() != allEvolvers.size())
                throw new RuntimeException("size isn't equals");

            for (int index = 0; index < evolutionsId.size(); index++) {
                Integer evolutionId = toType(evolutionsId.get(index), Integer.class);
                NativeArray evolvers = toType(allEvolvers.get(index), NativeArray.class);
                Integer mat1 = toType(evolvers.get(0), Integer.class);
                Integer mat2 = toType(evolvers.get(0), Integer.class);
                Integer mat3 = toType(evolvers.get(0), Integer.class);
                Integer mat4 = toType(evolvers.get(0), Integer.class);
                Integer mat5 = toType(evolvers.get(0), Integer.class);

                list.add(new Evolution(evolutionId, unitId, mat1, mat2, mat3, mat4, mat5));

            }

        }
        return list;
    }
}
