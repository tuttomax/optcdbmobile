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

            Object globalObj = flagsObj.get("global");
            Object rrObj = flagsObj.get("rr");
            Object lrrObj = flagsObj.get("lrr");
            Object rroObj = flagsObj.get("rro");
            Object promoObj = flagsObj.get("promo");
            Object shopObj = flagsObj.get("shop");
            Object specialObj = flagsObj.get("special");


            Boolean global = globalObj == null ? Boolean.FALSE : toType(globalObj, Boolean.class);
            Boolean rr = globalObj == null ? Boolean.FALSE : toType(rrObj, Boolean.class);
            Boolean lrr = globalObj == null ? Boolean.FALSE : toType(lrrObj, Boolean.class);
            Boolean rro = globalObj == null ? Boolean.FALSE : toType(rroObj, Boolean.class);
            Boolean promo = globalObj == null ? Boolean.FALSE : toType(promoObj, Boolean.class);
            Boolean shop = globalObj == null ? Boolean.FALSE : toType(shopObj, Boolean.class);
            Boolean special = globalObj == null ? Boolean.FALSE : toType(specialObj, Boolean.class);

            list.add(new Tag(unitId, global, rro, rr, lrr, promo, shop, special));
        }

        return list;

    }
}