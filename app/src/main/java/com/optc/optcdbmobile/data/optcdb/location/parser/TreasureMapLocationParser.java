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

import com.optc.optcdbmobile.data.database.entities.Location;
import com.optc.optcdbmobile.data.database.entities.TreasureLocation;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreasureMapLocationParser extends LocationParser {

    public TreasureMapLocationParser(byte type) {
        super(type);
    }

    @Override
    public List<Object> parse(Object jsParsed, int lastUsedId) {
        int startId = lastUsedId + 1;
        List<Object> list = new ArrayList<>();
        NativeArray array = (NativeArray) jsParsed;


        Location location;
        TreasureLocation treasureLocation;

        for (int index = 0; index < array.size(); index++) {
            NativeObject obj = (NativeObject) array.get(index);

            location = parseGeneralData(obj, startId);
            list.add(location);

            treasureLocation = new TreasureLocation(startId, location.getName());
            list.add(treasureLocation);

            parseDataDrops(obj, startId);

            startId++;
        }

        return list;
    }


    @Override
    protected List<String> getExclude() {
        return Arrays.asList("name", "thumb", "global", "gamewith", "nakama");
    }
}
