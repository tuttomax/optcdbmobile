package com.optc.optcdbmobile.data.optcdb.location.entities;

import java.util.HashMap;
import java.util.List;

@Deprecated
public class TreasureMapDrops extends Drops {

    private final HashMap<String, List<Integer>> Seas = new HashMap<>();

    public HashMap<String, List<Integer>> getSeas() {
        return Seas;
    }
}
