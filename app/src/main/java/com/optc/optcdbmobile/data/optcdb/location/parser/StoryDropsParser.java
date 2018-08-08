package com.optc.optcdbmobile.data.optcdb.location.parser;

import com.optc.optcdbmobile.data.optcdb.BaseParser;
import com.optc.optcdbmobile.data.optcdb.Constants;
import com.optc.optcdbmobile.data.optcdb.location.entities.StoryDrops;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class StoryDropsParser extends BaseParser<List<StoryDrops>> {
    @Override
    public List<StoryDrops> parse(Object element) {
        NativeArray array = (NativeArray) element;
        List<StoryDrops> list = new ArrayList<StoryDrops>();

        for (int index = 0; index < array.size(); index++) {

            StoryDrops storyDrops = new StoryDrops();

            NativeObject obj = (NativeObject) array.get(index);

            storyDrops.setName(toType(obj.get("name"), String.class));
            storyDrops.setThumb(toType(obj.get("thumb"), Integer.class));
            storyDrops.setGlobal(toType(obj.get("global"), Boolean.class));
            storyDrops.setNakama(toType(obj.get("nakama"), Integer.class));
            storyDrops.setGamewith(toType(obj.get("gamewith"), Integer.class));
            storyDrops.setCompletion(toType(obj.get("completion"), String.class));

            NativeArray completionUnit = (NativeArray) obj.get("Completion Units");
            if (completionUnit != null && completionUnit.size() > 0) {
                for (int completionIndex = 0; completionIndex < completionUnit.size(); completionIndex++) {
                    storyDrops.CompletionUnits.add(toType(completionUnit.get(completionIndex), Integer.class));
                }
            }


            for (int stageIndex = 1; stageIndex <= Constants.MAX_STAGE_IN_STORY; stageIndex++) {
                String fmt = String.format("%02d", stageIndex);
                NativeArray internal_array = (NativeArray) obj.get(fmt);
                if (internal_array != null && internal_array.size() > 0)
                    storyDrops.DropsOnStages.add(parseStage(internal_array));
            }

            list.add(storyDrops);
        }

        return list;
    }

    private List<Integer> parseStage(NativeArray stage) {
        List<Integer> list = new ArrayList<>();
        for (int index = 0; index < stage.size(); index++) {
            list.add(toType(stage.get(index), Integer.class));
        }
        return list;
    }
}

