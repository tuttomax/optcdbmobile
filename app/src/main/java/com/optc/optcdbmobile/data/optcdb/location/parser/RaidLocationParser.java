package com.optc.optcdbmobile.data.optcdb.location.parser;

import com.optc.optcdbmobile.data.database.entities.Location;
import com.optc.optcdbmobile.data.database.entities.RaidLocation;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RaidLocationParser extends LocationParser {
    public RaidLocationParser(byte type) {
        super(type);
    }

    @Override
    public List<Object> parse(Object jsParsed, int lastUsedId) {
        List<Object> list = new ArrayList<>();
        int startId = lastUsedId + 1;
        NativeArray array = (NativeArray) jsParsed;

        Location location;
        RaidLocation raidLocation;


        for (int index = 0; index < array.size(); index++) {
            NativeObject obj = (NativeObject) array.get(index);
            location = parseGeneralData(obj, startId);
            list.add(location);

            String slefty = toType(obj.get("slefty"), String.class);
            String condition = toType(obj.get("condition"), String.class);

            raidLocation = new RaidLocation(startId, slefty, condition);
            list.add(raidLocation);

            list.addAll(parseDataDrops(obj, startId));

            startId++;
        }

        return list;

    }

    @Override
    protected List<String> getExclude() {
        return Arrays.asList("name", "thumb", "global", "nakama", "gamewith", "slefty", "condition");
    }
}
