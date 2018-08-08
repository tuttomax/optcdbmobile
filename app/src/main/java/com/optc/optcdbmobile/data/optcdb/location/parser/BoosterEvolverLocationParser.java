package com.optc.optcdbmobile.data.optcdb.location.parser;

import com.optc.optcdbmobile.data.database.entities.BoosterEvolverLocation;
import com.optc.optcdbmobile.data.database.entities.Location;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoosterEvolverLocationParser extends LocationParser {

    @Override
    protected List<String> getExclude() {
        return Arrays.asList("name", "thumb", "global", "gamewith", "nakama", "day");
    }

    public BoosterEvolverLocationParser(byte type) {
        super(type);
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
