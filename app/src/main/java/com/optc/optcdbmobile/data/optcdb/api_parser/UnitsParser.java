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

package com.optc.optcdbmobile.data.optcdb.api_parser;

import com.optc.optcdbmobile.data.database.entities.Unit;
import com.optc.optcdbmobile.data.optcdb.BaseParser;

import org.mozilla.javascript.NativeArray;

import java.util.ArrayList;
import java.util.List;

public class UnitsParser extends BaseParser<List<Unit>> {
    private int id;

    @Override
    public List<Unit> parse(Object jsParsed) {

        final List<NativeArray> temp = (List<NativeArray>) jsParsed;

        final List<Unit> list = new ArrayList<>();

        id = 0;
        String[] types;
        String[] classes;
        Float stars;
        Byte cost, combo, socket, maxLevel;
        Integer expToMax, level1HP, maxHP, level1ATK, maxATK, level1RCV, maxRCV;

        Unit unit;
        //id used for foreign key constraint equals database id
        int reference_id = 0;

        int null_count = 0;

        for (NativeArray array : temp) {
            int index = 0;

            reference_id++;
            String name = toType(array.get(index++), String.class);


            if (name == null || name.isEmpty()) {
                null_count++;
                continue;
            }

            /*  HACK
             *  Needed for null characters between valid character which id should not be skipped
             */
            if (null_count > 0 && null_count < 10) {
                null_count = 0;
                id += null_count;
            } else ++id;


            Object obj = array.get(index++);
            types = parseTypes(obj);

            obj = array.get(index++);
            classes = parseClasses(obj);

            obj = array.get(index++);
            stars = parseStars(obj);

            cost = toType(array.get(index++), Byte.class);
            combo = toType(array.get(index++), Byte.class);
            socket = toType(array.get(index++), Byte.class);
            maxLevel = toType(array.get(index++), Byte.class);
            expToMax = toType(array.get(index++), Integer.class);
            level1HP = toType(array.get(index++), Integer.class);
            level1ATK = toType(array.get(index++), Integer.class);
            level1RCV = toType(array.get(index++), Integer.class);
            maxHP = toType(array.get(index++), Integer.class);
            maxATK = toType(array.get(index++), Integer.class);
            maxRCV = toType(array.get(index++), Integer.class);
            //int growth = array.get(index);

            unit = new Unit(reference_id, id, name, classes[0], classes[1], types[0], types[1], expToMax, maxLevel,
                    level1ATK, maxATK, level1HP, maxHP, level1RCV, maxRCV,
                    cost, combo, socket, stars);


            list.add(unit);
        }

        return list;
    }

    private Float parseStars(Object stars) {
        Float value = null;
        if (stars instanceof String) {
            if (stars.equals("6+")) {
                value = 6.5f;
            } else if (stars.equals("5+")) {
                value = 5.5f;
            }
        } else if (stars instanceof Double) {
            value = toType(stars, Float.class);
        } else if (stars == null) {
            value = null;
        }
        return value;
    }

    private String[] parseClasses(Object obj) {
        String class1 = null;
        String class2 = null;
        if (obj == null) {
            class1 = null;
            class2 = null;
        } else if (obj instanceof String) {
            class1 = toType(obj, String.class);
            class2 = null;
        } else if (obj instanceof NativeArray) {
            NativeArray classArray = (NativeArray) obj;
            if (classArray.size() == 1) {
                class1 = toType(classArray.get(0), String.class);
                class2 = null;
            } else {
                class1 = toType(classArray.get(0), String.class);
                class2 = toType(classArray.get(1), String.class);
            }
        }

        return new String[]{class1, class2};
    }

    private String[] parseTypes(Object obj) {
        String type1 = null;
        String type2 = null;
        if (obj == null) {
            type1 = null;
            type2 = null;
        } else if (obj instanceof String) {
            type1 = toType(obj, String.class);
            type2 = null;
        } else if (obj instanceof NativeArray) {
            NativeArray typeArray = (NativeArray) obj;
            if (typeArray.size() == 1) {
                type1 = toType(typeArray.get(0), String.class);
                type2 = null;
            } else {
                type1 = toType(typeArray.get(0), String.class);
                type2 = toType(typeArray.get(1), String.class);
            }
        }

        return new String[]{type1, type2};
    }


}
