package com.optc.optcdbmobile.data.database.filters;


import java.util.HashMap;
import java.util.Map;

public class FilterType {

    //INFO: Always power of 2 otherwise bit operation doesn't work
    public static final int HEADER = 1;
    public static final int COLOR = 2;
    public static final int CLASS = 4;
    public static final int RARITY = 8;
    public static final int COST = 16;
    public static final int DROP = 32;
    public static final int EXCLUSION = 64;
    public static final int TREASURE_MAP = 128;
    public static final int CAPTAIN = 256;
    public static final int SPECIAL = 512;
    public static final int SAILOR = 1024;
    public static final int LIMIT = 2048;

    private final static Map<Integer, String> nameMap = new HashMap<Integer, String>() {{
        put(COLOR, "Colors");
        put(CLASS, "Classes");
        put(RARITY, "Rarity");
        put(COST, "Cost");
        put(DROP, "Drop");
        put(EXCLUSION, "Exclusion");
        put(TREASURE_MAP, "Treasure map");
        put(CAPTAIN, "Captain");
        put(SPECIAL, "Special");
        put(SAILOR, "Sailor");
        put(LIMIT, "Limit");
    }};

    public static final String name(int id) {
        return nameMap.get(id);
    }
}
