package com.optc.optcdbmobile.data.optcdb.location.parser;

import com.optc.optcdbmobile.data.database.entities.Location;
import com.optc.optcdbmobile.data.database.entities.StoryLocation;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StoryLocationParser extends LocationParser {


    public StoryLocationParser(byte type) {
        super(type);
    }

    @Override
    public List<Object> parse(Object jsParsed, int lastUsedId) {
        int startId = lastUsedId + 1;
        List<Object> list = new ArrayList<>();

        Location location;
        StoryLocation storyLocation;

        NativeArray array = (NativeArray) jsParsed;

        for (int index = 0; index < array.size(); index++) {
            NativeObject obj = (NativeObject) array.get(index);

            location = parseGeneralData(obj, startId);
            list.add(location);

            String completion = toType(obj.get("completion"), String.class);
            storyLocation = new StoryLocation(startId, completion);

            list.addAll(parseDataDrops(obj, startId));

            startId++;
        }
        return list;
    }

    @Override
    protected List<String> getExclude() {
        return Arrays.asList("name", "thumb", "global", "nakama", "gamewith", "completion");
    }
}
