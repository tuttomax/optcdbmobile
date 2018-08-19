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

package com.optc.optcdbmobile.data.optcdb.location.parser;

import com.optc.optcdbmobile.data.database.entities.BoosterEvolverLocation;
import com.optc.optcdbmobile.data.database.entities.Location;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoosterEvolverLocationParser extends LocationParser {

    public BoosterEvolverLocationParser(byte type) {
        super(type);
    }

    @Override
    protected List<String> getExclude() {
        return Arrays.asList("name", "thumb", "global", "gamewith", "nakama", "day");
    }

    @Override
    public List<Object> parse(Object jsParsed, int lastUsedId) {
        List<Object> list = new ArrayList<>();
        int startId = lastUsedId + 1;

        NativeArray array = (NativeArray) jsParsed;

        Location location;
        BoosterEvolverLocation boosterEvolverLocation;


        for (int index = 0; index < array.size(); index++) {
            NativeObject obj = (NativeObject) array.get(index);

            Byte day = toType(obj.get("day"), Byte.class);

            location = parseGeneralData(obj, startId);
            list.add(location);

            boosterEvolverLocation = new BoosterEvolverLocation(startId, day);
            list.add(boosterEvolverLocation);

            list.addAll(parseDataDrops(obj, startId));

            startId++;
        }

        return list;

    }
}
