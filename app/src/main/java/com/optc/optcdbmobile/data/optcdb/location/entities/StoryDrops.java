package com.optc.optcdbmobile.data.optcdb.location.entities;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class StoryDrops extends Drops {

    public final List<Integer> CompletionUnits = new ArrayList<>();
    public final List<List<Integer>> DropsOnStages = new ArrayList<>();
    public String completion;

    public String getCompletion() {
        return completion;
    }

    public void setCompletion(String completion) {
        this.completion = completion;
    }
}
