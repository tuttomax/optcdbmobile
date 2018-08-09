package com.optc.optcdbmobile.data.optcdb.entities.location.entities;

import com.optc.optcdbmobile.data.optcdb.BaseParser;
import com.optc.optcdbmobile.data.optcdb.Constants;
import com.optc.optcdbmobile.data.optcdb.entities.location.parser.BoosterEvolverDropsParser;
import com.optc.optcdbmobile.data.optcdb.entities.location.parser.ColiseumDropsParser;
import com.optc.optcdbmobile.data.optcdb.entities.location.parser.FortnightDropsParser;
import com.optc.optcdbmobile.data.optcdb.entities.location.parser.RaidDropsParser;
import com.optc.optcdbmobile.data.optcdb.entities.location.parser.SpecialDropsParser;
import com.optc.optcdbmobile.data.optcdb.entities.location.parser.StoryDropsParser;
import com.optc.optcdbmobile.data.optcdb.entities.location.parser.TraningForestDropsParser;
import com.optc.optcdbmobile.data.optcdb.entities.location.parser.TreasureMapDropsParser;

import java.util.HashMap;
import java.util.Map;

public abstract class Drops {
    final static Map<String, Byte> table = new HashMap<String, Byte>() {{
        put("Story Island", Constants.STORY_TYPE);
        put("Booster and Evolver Island", Constants.BOOSTER_EVOLVER_TYPE);
        put("Fortnight", Constants.FORTNIGHT_TYPE);
        put("RaidLocation", Constants.RAID_TYPE);
        put("Coliseum", Constants.COLISEUM_TYPE);
        put("Treasure Map", Constants.TREASURE_MAP_TYPE);
        put("Special", Constants.SPECIAL_TYPE);
        put("Training Forest", Constants.TRANING_FOREST_TYPE);
    }};
    final static Map<Byte, BaseParser> parser_table = new HashMap<Byte, BaseParser>() {{
        put(Constants.STORY_TYPE, new StoryDropsParser());
        put(Constants.BOOSTER_EVOLVER_TYPE, new BoosterEvolverDropsParser());
        put(Constants.FORTNIGHT_TYPE, new FortnightDropsParser());
        put(Constants.RAID_TYPE, new RaidDropsParser());
        put(Constants.COLISEUM_TYPE, new ColiseumDropsParser());
        put(Constants.TREASURE_MAP_TYPE, new TreasureMapDropsParser());
        put(Constants.SPECIAL_TYPE, new SpecialDropsParser());
        put(Constants.TRANING_FOREST_TYPE, new TraningForestDropsParser());
    }};
    private String name;
    private Integer thumb;
    private Boolean global;
    private Integer nakama;
    private Integer gamewith;
    //TODO Insert getExclude() as PROTECTED
    //TODO Insert getExcludeExtended() as ABSTRACT

    public static byte getType(final String str) {
        return table.get(str);
    }

    public static BaseParser getParser(final byte type) {
        return parser_table.get(type);
    }

    public Integer getNakama() {
        return nakama;
    }

    public void setNakama(Integer nakama) {
        this.nakama = nakama;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }

    public Integer getGamewith() {
        return gamewith;
    }

    public void setGamewith(Integer gamewith) {
        this.gamewith = gamewith;
    }

    public Integer getThumb() {
        return thumb;
    }

    public void setThumb(Integer thumb) {
        this.thumb = thumb;
    }

}
