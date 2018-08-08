package com.optc.optcdbmobile.data.optcdb.location.entities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Deprecated
public class SpecialDrops extends FortnightDrops {

    private static final List<String> exclude = Arrays.asList("name", "thumb", "global", "nakama", "gamewith",
            "challenge", "challengeData", "condition", "showManual",
            "All Difficulties", "Rookie", "Veteran", "Elite", "Expert");

    private final HashMap<String, List<Integer>> Data = new HashMap<>();
    private Boolean showManual;

    public static List<String> getExclude() {
        return exclude;
    }

    public Boolean getShowManual() {
        return showManual;
    }

    public void setShowManual(Boolean showManual) {
        this.showManual = showManual;
    }

    public HashMap<String, List<Integer>> getData() {
        return Data;
    }
}
