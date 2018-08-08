package com.optc.optcdbmobile.data.optcdb.location.parser;

import com.optc.optcdbmobile.data.database.entities.FortnightLocation;
import com.optc.optcdbmobile.data.database.entities.Location;
import com.optc.optcdbmobile.data.database.entities.LocationChallengeData;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FortnightLocationParser extends LocationParser {
    public FortnightLocationParser(byte type) {
        super(type);
    }

    @Override
    public List<Object> parse(Object jsParsed, int lastUsedId) {
        int startId = lastUsedId + 1;

        List<Object> list = new ArrayList<>();
        Location location;
        FortnightLocation fortnightLocation;
        LocationChallengeData locationChallengeData;

        NativeArray array = (NativeArray) jsParsed;
        for (int index = 0; index < array.size(); index++) {
            NativeObject obj = (NativeObject) array.get(index);

            location = parseGeneralData(obj, startId);
            list.add(location);


            String condition = toType(obj.get("condition"), String.class);
            String challenge = toType(obj.get("challenge"), String.class);

            fortnightLocation = new FortnightLocation(startId, condition, challenge);
            list.add(fortnightLocation);

            list.addAll(parseChallengeData(obj, startId));

            list.addAll(parseDataDrops(obj, startId));

            startId++;

        }
        return list;
    }

    @Override
    protected List<String> getExclude() {
        return Arrays.asList("name", "thumb", "global", "gamewith", "nakama", "condition", "challenge");
    }
}
