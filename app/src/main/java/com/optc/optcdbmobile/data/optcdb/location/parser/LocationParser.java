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
import com.optc.optcdbmobile.data.database.entities.LocationChallengeData;
import com.optc.optcdbmobile.data.database.entities.LocationDrops;
import com.optc.optcdbmobile.data.optcdb.BaseParser;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class LocationParser extends BaseParser<List<Object>> {

    private byte type;

    public LocationParser(byte type) {
        this.type = type;
    }

    public abstract List<Object> parse(Object jsParsed, int lastUsedId);

    protected abstract List<String> getExclude();

    public byte getType() {
        return type;
    }

    @Deprecated
    @Override
    public List<Object> parse(Object jsParsed) {
        return null;
    }

    protected Location parseGeneralData(NativeObject obj, int id) {

        String name = toType(obj.get("name"), String.class);
        Integer thumb = toType(obj.get("thumb"), Integer.class);
        Boolean global = toType(obj.get("global"), Boolean.class);
        Integer gamewith = toType(obj.get("gamewith"), Integer.class);
        Integer nakama = toType(obj.get("nakama"), Integer.class);

        return new Location(id, name, getType(), thumb, global, gamewith, nakama);
    }

    protected List<LocationDrops> parseDataDrops(NativeObject obj, int startId) {
        LocationDrops locationDrops;
        final List<LocationDrops> list = new ArrayList<>();
        for (Map.Entry<Object, Object> entry : obj.entrySet()) {
            String dropsName = entry.getKey().toString(); //like Master,Global,All Difficulties,01,02,eccc...

            if (!getExclude().contains(entry.getKey().toString())) {
                Object _obj = entry.getValue();
                NativeArray dropsArray = (NativeArray) _obj;
                for (int dropsIndex = 0; dropsIndex < dropsArray.size(); dropsIndex++) {

                    Integer unitId = toType(dropsArray.get(dropsIndex), Integer.class);

                    locationDrops = new LocationDrops(
                            startId,
                            dropsName,
                            dropsIndex,
                            unitId);
                    list.add(locationDrops);
                }
            }
        }

        return list;
    }


    protected List<LocationChallengeData> parseChallengeData(NativeObject obj, int startId) {
        LocationChallengeData locationChallengeData;
        List<LocationChallengeData> list = new ArrayList<>();

        NativeArray challengeDataArray = toType(obj.get("challengeData"), NativeArray.class);

        if (challengeDataArray != null) {
            for (int dataArrayIndex = 0; dataArrayIndex < challengeDataArray.size(); dataArrayIndex++) {
                NativeArray challengeElement = (NativeArray) challengeDataArray.get(dataArrayIndex);

                String predicate = toType(challengeElement.get(0), String.class);
                String reward = toType(challengeElement.get(1), String.class);

                locationChallengeData = new LocationChallengeData(startId, dataArrayIndex, predicate, reward);
                list.add(locationChallengeData);
            }
        }
        return list;
    }
}
