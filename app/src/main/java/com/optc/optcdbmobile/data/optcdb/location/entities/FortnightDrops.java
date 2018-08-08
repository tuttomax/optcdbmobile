package com.optc.optcdbmobile.data.optcdb.location.entities;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class FortnightDrops extends Drops {


    private final List<List<String>> ChallengeData = new ArrayList<>();
    private final SparseArray<List<Integer>> DropsOnDifficulties = new SparseArray<>();
    private String condition;
    private String challenge;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public List<List<String>> getChallengeData() {
        return ChallengeData;
    }

    public SparseArray<List<Integer>> getDropsOnDifficulties() {
        return DropsOnDifficulties;
    }
}
