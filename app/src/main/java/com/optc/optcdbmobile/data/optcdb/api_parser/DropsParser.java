package com.optc.optcdbmobile.data.optcdb.api_parser;

import com.optc.optcdbmobile.data.database.entities.BoosterEvolverLocation;
import com.optc.optcdbmobile.data.database.entities.ColiseumLocation;
import com.optc.optcdbmobile.data.database.entities.FortnightLocation;
import com.optc.optcdbmobile.data.database.entities.RaidLocation;
import com.optc.optcdbmobile.data.database.entities.SpecialLocation;
import com.optc.optcdbmobile.data.database.entities.StoryLocation;
import com.optc.optcdbmobile.data.database.entities.TrainingForestLocation;
import com.optc.optcdbmobile.data.database.entities.TreasureLocation;
import com.optc.optcdbmobile.data.optcdb.BaseParser;
import com.optc.optcdbmobile.data.optcdb.Constants;
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
        put("Story Island", Constants.STORY_TYPE);
        put("Booster and Evolver Island", Constants.BOOSTER_EVOLVER_TYPE);
        put("Fortnight", Constants.FORTNIGHT_TYPE);
        put("Raid", Constants.RAID_TYPE);
        put("Coliseum", Constants.COLISEUM_TYPE);
        put("Treasure Map", Constants.TREASURE_MAP_TYPE);
        put("Special", Constants.SPECIAL_MAP_TYPE);
        put("Training Forest", Constants.TRAINING_FOREST_TYPE);
    }};
    private final static Map<Byte, LocationParser> parserTable = new HashMap<Byte, LocationParser>() {{
        put(Constants.STORY_TYPE, new StoryLocationParser(Constants.STORY_TYPE));
        put(Constants.BOOSTER_EVOLVER_TYPE, new BoosterEvolverLocationParser(Constants.BOOSTER_EVOLVER_TYPE));
        put(Constants.FORTNIGHT_TYPE, new FortnightLocationParser(Constants.FORTNIGHT_TYPE));
        put(Constants.RAID_TYPE, new RaidLocationParser(Constants.RAID_TYPE));
        put(Constants.COLISEUM_TYPE, new ColiseumLocationParser(Constants.COLISEUM_TYPE));
        put(Constants.TREASURE_MAP_TYPE, new TreasureMapLocationParser(Constants.TREASURE_MAP_TYPE));
        put(Constants.SPECIAL_MAP_TYPE, new SpecialLocationParser(Constants.SPECIAL_MAP_TYPE));
        put(Constants.TRAINING_FOREST_TYPE, new TrainingForestLocationParser(Constants.TRAINING_FOREST_TYPE));
    }};

    /* Will be used when will be populate database */
    private final static Map<Byte, Class> classTable = new HashMap<Byte, Class>() {{
        put(Constants.STORY_TYPE, StoryLocation.class);
        put(Constants.BOOSTER_EVOLVER_TYPE, BoosterEvolverLocation.class);
        put(Constants.FORTNIGHT_TYPE, FortnightLocation.class);
        put(Constants.RAID_TYPE, RaidLocation.class);
        put(Constants.COLISEUM_TYPE, ColiseumLocation.class);
        put(Constants.TREASURE_MAP_TYPE, TreasureLocation.class);
        put(Constants.SPECIAL_MAP_TYPE, SpecialLocation.class);
        put(Constants.TRAINING_FOREST_TYPE, TrainingForestLocation.class);
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
