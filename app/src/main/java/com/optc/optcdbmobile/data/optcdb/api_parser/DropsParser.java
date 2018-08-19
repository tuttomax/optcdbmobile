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

import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.database.entities.BoosterEvolverLocation;
import com.optc.optcdbmobile.data.database.entities.ColiseumLocation;
import com.optc.optcdbmobile.data.database.entities.FortnightLocation;
import com.optc.optcdbmobile.data.database.entities.RaidLocation;
import com.optc.optcdbmobile.data.database.entities.SpecialLocation;
import com.optc.optcdbmobile.data.database.entities.StoryLocation;
import com.optc.optcdbmobile.data.database.entities.TrainingForestLocation;
import com.optc.optcdbmobile.data.database.entities.TreasureLocation;
import com.optc.optcdbmobile.data.optcdb.BaseParser;
import com.optc.optcdbmobile.data.optcdb.location.parser.BoosterEvolverLocationParser;
import com.optc.optcdbmobile.data.optcdb.location.parser.ColiseumLocationParser;
import com.optc.optcdbmobile.data.optcdb.location.parser.FortnightLocationParser;
import com.optc.optcdbmobile.data.optcdb.location.parser.LocationParser;
import com.optc.optcdbmobile.data.optcdb.location.parser.RaidLocationParser;
import com.optc.optcdbmobile.data.optcdb.location.parser.SpecialLocationParser;
import com.optc.optcdbmobile.data.optcdb.location.parser.StoryLocationParser;
import com.optc.optcdbmobile.data.optcdb.location.parser.TrainingForestLocationParser;
import com.optc.optcdbmobile.data.optcdb.location.parser.TreasureMapLocationParser;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DropsParser extends BaseParser<List<Object>> {

    private final static Map<String, Byte> stringTable = new HashMap<String, Byte>() {{
        put("Story Island", Constants.DropsType.STORY_TYPE);
        put("Booster and Evolver Island", Constants.DropsType.BOOSTER_EVOLVER_TYPE);
        put("Fortnight", Constants.DropsType.FORTNIGHT_TYPE);
        put("Raid", Constants.DropsType.RAID_TYPE);
        put("Coliseum", Constants.DropsType.COLISEUM_TYPE);
        put("Treasure Map", Constants.DropsType.TREASURE_MAP_TYPE);
        put("Special", Constants.DropsType.SPECIAL_MAP_TYPE);
        put("Training Forest", Constants.DropsType.TRAINING_FOREST_TYPE);
    }};
    private final static Map<Byte, LocationParser> parserTable = new HashMap<Byte, LocationParser>() {{
        put(Constants.DropsType.STORY_TYPE, new StoryLocationParser(Constants.DropsType.STORY_TYPE));
        put(Constants.DropsType.BOOSTER_EVOLVER_TYPE, new BoosterEvolverLocationParser(Constants.DropsType.BOOSTER_EVOLVER_TYPE));
        put(Constants.DropsType.FORTNIGHT_TYPE, new FortnightLocationParser(Constants.DropsType.FORTNIGHT_TYPE));
        put(Constants.DropsType.RAID_TYPE, new RaidLocationParser(Constants.DropsType.RAID_TYPE));
        put(Constants.DropsType.COLISEUM_TYPE, new ColiseumLocationParser(Constants.DropsType.COLISEUM_TYPE));
        put(Constants.DropsType.TREASURE_MAP_TYPE, new TreasureMapLocationParser(Constants.DropsType.TREASURE_MAP_TYPE));
        put(Constants.DropsType.SPECIAL_MAP_TYPE, new SpecialLocationParser(Constants.DropsType.SPECIAL_MAP_TYPE));
        put(Constants.DropsType.TRAINING_FOREST_TYPE, new TrainingForestLocationParser(Constants.DropsType.TRAINING_FOREST_TYPE));
    }};

    /* Will be used when will be populate database */
    private final static Map<Byte, Class> classTable = new HashMap<Byte, Class>() {{
        put(Constants.DropsType.STORY_TYPE, StoryLocation.class);
        put(Constants.DropsType.BOOSTER_EVOLVER_TYPE, BoosterEvolverLocation.class);
        put(Constants.DropsType.FORTNIGHT_TYPE, FortnightLocation.class);
        put(Constants.DropsType.RAID_TYPE, RaidLocation.class);
        put(Constants.DropsType.COLISEUM_TYPE, ColiseumLocation.class);
        put(Constants.DropsType.TREASURE_MAP_TYPE, TreasureLocation.class);
        put(Constants.DropsType.SPECIAL_MAP_TYPE, SpecialLocation.class);
        put(Constants.DropsType.TRAINING_FOREST_TYPE, TrainingForestLocation.class);
    }};

    @Override
    public List<Object> parse(Object jsParsed) {
        NativeObject root = (NativeObject) jsParsed;

        List<Object> list = new ArrayList<>();

        int lastIndex = 0; //used for generating unique id

        for (Map.Entry<Object, Object> entry : root.entrySet()) {
            String locationType = (String) entry.getKey();
            NativeArray array = (NativeArray) entry.getValue();
            byte type = stringTable.get(locationType);
            LocationParser parser = parserTable.get(type);

            List<Object> internalList = parser.parse(array, lastIndex);
            list.addAll(internalList);

            lastIndex += internalList.size() - 1;
        }

        return list;
    }
}
