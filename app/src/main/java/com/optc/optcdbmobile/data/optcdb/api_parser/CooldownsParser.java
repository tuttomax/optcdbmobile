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

import com.optc.optcdbmobile.data.optcdb.BaseParser;
import com.optc.optcdbmobile.data.optcdb.entities.Cooldown;

import org.mozilla.javascript.NativeArray;

import java.util.ArrayList;
import java.util.List;

public class CooldownsParser extends BaseParser<List<Cooldown>> {

    @Override
    public List<Cooldown> parse(Object jsParsed) {
        List<Cooldown> list = new ArrayList<>();
        List<Object> temp = (List<Object>) jsParsed;
        int index = 1;
        for (Object element : temp) {
            if (element == null) {
                index++;
            } else if (element instanceof Double) {
                Double d = (Double) element;
                Byte b = d.byteValue();
                list.add(new Cooldown(index, b, b));
            } else if (element instanceof NativeArray) {
                NativeArray array = (NativeArray) element;
                Byte min = toType(array.get(0), Byte.class);
                Byte max = toType(array.get(1), Byte.class);
                list.add(new Cooldown(index, min, max));
            }
        }
        return list;
    }
}
