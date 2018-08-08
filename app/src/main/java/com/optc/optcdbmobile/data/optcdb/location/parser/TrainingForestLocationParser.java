package com.optc.optcdbmobile.data.optcdb.location.parser;

import com.optc.optcdbmobile.data.database.entities.Location;
import com.optc.optcdbmobile.data.database.entities.TrainingForestLocation;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import java.util.ArrayList;
import java.util.List;

public class TrainingForestLocationParser extends LocationParser {

    public TrainingForestLocationParser(byte type) {
        super(type);
    }

    @Override
    public List<Object> parse(Object jsParsed, int lastUsedId) {
        int startId = lastUsedId + 1;
        List<Object> list = new ArrayList<>();

        NativeArray array = (NativeArray) jsParsed;

        Location location;
        TrainingForestLocation trainingForestLocation;

        for (int index = 0; index < array.size(); index++) {
            NativeObject obj = (NativeObject) array.get(index);

            location = parseGeneralData(obj, startId);
            list.add(location);

            String completion = toType(obj.get("completion"), String.class);
            String slefty = toType(obj.get("slefty"), String.class);
            trainingForestLocation = new TrainingForestLocation(startId, slefty, completion);
            list.add(trainingForestLocation);

            startId++;
        }

        return list;
    }

    @Override
    protected List<String> getExclude() {
        // this won't be used
        return null;
    }
}
