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

import com.optc.optcdbmobile.data.database.entities.Tag;
import com.optc.optcdbmobile.data.optcdb.BaseParser;

import org.mozilla.javascript.NativeObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TagParser extends BaseParser<List<Tag>> {
    @Override
    public List<Tag> parse(Object jsParsed) {
        NativeObject rootObj = (NativeObject) jsParsed;
        List<Tag> list = new ArrayList<>();

        for (Map.Entry<Object, Object> entry : rootObj.entrySet()) {

            Integer unitId = toType(entry.getKey(), Integer.class);

            NativeObject flagsObj = (NativeObject) entry.getValue();

            Boolean global = toType(flagsObj.get("global"), Boolean.class);
            Boolean rr = toType(flagsObj.get("rr"), Boolean.class);
            Boolean lrr = toType(flagsObj.get("lrr"), Boolean.class);
            Boolean rro = toType(flagsObj.get("rro"), Boolean.class);
            Boolean promo = toType(flagsObj.get("promo"), Boolean.class);
            Boolean shop = toType(flagsObj.get("shop"), Boolean.class);
            Boolean special = toType(flagsObj.get("special"), Boolean.class);

            list.add(new Tag(unitId, global, rro, rr, lrr, promo, shop, special));
        }

        return list;

    }
}