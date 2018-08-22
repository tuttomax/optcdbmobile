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


import com.optc.optcdbmobile.data.database.entities.Alias;
import com.optc.optcdbmobile.data.optcdb.BaseParser;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AliasParser extends BaseParser<List<Alias>> {
    @Override
    public List<Alias> parse(Object jsParsed) {
        NativeObject rootObj = (NativeObject) jsParsed;
        List<Alias> list = new ArrayList<>();

        for (Map.Entry<Object, Object> entry : rootObj.entrySet()) {
            Integer unitId = toType(entry.getKey(), Integer.class);

            NativeArray aliasArray = toType(entry.getValue(), NativeArray.class);
            for (int index = 0; index < aliasArray.size(); index++) {
                String aliasName = toType(aliasArray.get(index), String.class);
                list.add(new Alias(unitId, index, aliasName));
            }
        }
        return list;
    }
}
