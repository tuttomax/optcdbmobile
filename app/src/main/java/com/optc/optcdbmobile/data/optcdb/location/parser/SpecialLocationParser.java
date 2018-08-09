package com.optc.optcdbmobile.data.optcdb.location.parser;

import com.optc.optcdbmobile.data.database.entities.Location;
import com.optc.optcdbmobile.data.database.entities.SpecialLocation;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpecialLocationParser extends LocationParser {

    public SpecialLocationParser(byte type) {
        super(type);
    }

    @Override
    public List<Object> parse(Object jsParsed, int lastUsedId) {
        List<Object> list = new ArrayList<>();
        int startId = lastUsedId + 1;
        NativeArray array = (NativeArray) jsParsed;

        Location location;
        SpecialLocation specialLocation;

        for (int index = 0; index < array.size(); index++) {

            NativeObject obj = (NativeObject) array.get(index);

            location = parseGeneralData(obj, startId);
            list.add(location);

            String condition = toType(obj.get("condition"), String.class);
            String challenge = toType(obj.get("challenge"), String.class);
            Boolean showManual = toType(obj.get("showManual"), Boolean.class);

            specialLocation = new SpecialLocation(startId, condition, challenge, showManual);
            list.add(specialLocation);

            list.addAll(parseDataDrops(obj, startId));
            list.addAll(parseChallengeData(obj, startId));

        }

        return list;
    }

    @Override
    protected List<String> getExclude() {
        return Arrays.asList("name", "thumb", "global", "nakama", "gamewith", "condition", "challenge", "showManual", "challengeData");
    }
}
