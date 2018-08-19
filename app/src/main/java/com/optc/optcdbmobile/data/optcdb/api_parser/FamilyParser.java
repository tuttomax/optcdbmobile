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


import com.optc.optcdbmobile.data.database.entities.Family;
import com.optc.optcdbmobile.data.optcdb.BaseParser;
import com.optc.optcdbmobile.data.optcdb.entities.FamilyContainer;

import org.mozilla.javascript.NativeArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FamilyParser extends BaseParser<List<FamilyContainer>> {
    @Override
    public List<FamilyContainer> parse(Object jsParsed) {
        NativeArray root = (NativeArray) jsParsed;
        List<FamilyContainer> list = new ArrayList<>();

        Map<String, List<Integer>> nameMap = new HashMap<>();

        for (int index = 0; index < root.size(); index++) {
            int unitId = index + 1;
            Object o = root.get(index);
            if (o == null) {
                continue;
            }
            if (o instanceof String) {
                String name = toType(root.get(index), String.class);
                if (!nameMap.containsKey(name)) {
                    nameMap.put(name, new ArrayList<Integer>());
                }
                nameMap.get(name).add(unitId);

            } else if (o instanceof NativeArray) {
                NativeArray array = (NativeArray) o;
                for (int internal_index = 0; internal_index < array.size(); internal_index++) {
                    String name = toType(array.get(internal_index), String.class);
                    if (!nameMap.containsKey(name)) {
                        nameMap.put(name, new ArrayList<Integer>());
                    }
                    nameMap.get(name).add(unitId);
                }
            }
        }

        Object[] keys = nameMap.keySet().toArray();
        for (int index = 0; index < keys.length; index++) {

            String name = (String) keys[index];
            list.add(new FamilyContainer(
                    new Family(index, name),
                    nameMap.get(name)));

        }

        return list;
    }
}
