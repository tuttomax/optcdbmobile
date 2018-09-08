package com.optc.optcdbmobile.data.ui.activities.general;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import com.optc.optcdbmobile.R;

import java.util.HashMap;
import java.util.Map;

public class UnitHelper {
    public static final String STR_STRING = "STR";
    public static final String QCK_STRING = "QCK";
    public static final String DEX_STRING = "DEX";
    public static final String PSY_STRING = "PSY";
    public static final String INT_STRING = "INT";

    public static final String FIGHTER_STRING = "Fighter";
    public static final String SLASHER_STRING = "Slasher";
    public static final String SHOOTER_STRING = "Shooter";
    public static final String STRIKER_STRING = "Striker";
    public static final String DRIVEN_STRING = "Driven";
    public static final String POWERHOUSE_STRING = "Powerhouse";
    public static final String FREE_SPIRIT_STRING = "Free Spirit";
    public static final String CEREBRAL_STRING = "Cerebral";
    public static final String BOOSTER = "Booster";
    public static final String EVOLVER = "Evolver";


    public static final String UNIT_PARCELLABLE = "__UNIT__";
    public static final String UNIT_ID = "__id__";
    private final static Map<String, Integer> colorsId = new HashMap<String, Integer>() {{
        put(STR_STRING, R.color.colorSTR);
        put(QCK_STRING, R.color.colorQCK);
        put(DEX_STRING, R.color.colorDEX);
        put(PSY_STRING, R.color.colorPSY);
        put(INT_STRING, R.color.colorINT);
    }};
    private final static Map<String, Integer> darkColorsId = new HashMap<String, Integer>() {{
        put(STR_STRING, R.color.colorDarkSTR);
        put(QCK_STRING, R.color.colorDarkQCK);
        put(DEX_STRING, R.color.colorDarkDEX);
        put(PSY_STRING, R.color.colorDarkPSY);
        put(INT_STRING, R.color.colorDarkINT);
    }};

    private final static Map<String, Integer> classId = new HashMap<String, Integer>() {{
        put(FIGHTER_STRING, R.drawable.ic_fighter);
        put(SLASHER_STRING, R.drawable.ic_slasher);
        put(SHOOTER_STRING, R.drawable.ic_shooter);
        put(STRIKER_STRING, R.drawable.ic_striker);
        put(DRIVEN_STRING, R.drawable.ic_driven);
        put(POWERHOUSE_STRING, R.drawable.ic_powerhouse);
        put(FREE_SPIRIT_STRING, R.drawable.ic_free_spirit);
        put(CEREBRAL_STRING, R.drawable.ic_cerebral);
        put(EVOLVER, R.drawable.ic_evolver);
        put(BOOSTER, R.drawable.ic_booster);

    }};

    private final static Map<String, Integer> notesResidMap = new HashMap<String, Integer>() {{
        put("captainProportional", R.string.captainProportional);
        put("captainFixed", R.string.captainFixed);
        put("fixed", R.string.fixed);
        put("gOrbs", R.string.gOrbs);
        put("noFixedPerc", R.string.noFixedPerc);
        put("orb", R.string.orb);
        put("poison", R.string.poison);
        put("toxic", R.string.toxic);
        put("random", R.string.random);
        put("randomHeal", R.string.randomHeal);
        put("randomHits", R.string.randomHits);
        put("specialProportional", R.string.specialProportional);
        put("stages", R.string.stages);
        put("silence", R.string.silence);
        put("rewind", R.string.rewind);
        put("ignoreBarrier", R.string.ignoreBarrier);
        put("zombie", R.string.zombie);
        put("colorAffinity", R.string.colorAffinity);
        put("instantKill", R.string.instantKill);
        put("additionalDamage", R.string.additionalDamage);
        put("beneficial", R.string.beneficial);
        put("enrage", R.string.enrage);

    }};


    public static final int THUMB_WIDTH = 96;
    public static final int THUMB_HEIGHT = 96;

    public static int getTypeColor(String colorName, Resources res) {
        return ResourcesCompat.getColor(res, colorsId.get(colorName), null);
    }

    private final static String packageName = "com.optc.optcdbmobile";

    public static String getStarsToString(Float stars) {
        if (stars == 5.5f) return "5+";
        else if (stars == 6.5f) return "6+";
        else return String.valueOf(stars.intValue());
    }

    public static Drawable getClassDrawable(String name, Resources res) {
        return ResourcesCompat.getDrawable(res, classId.get(name), null);
    }

    public static int getDarkTypeColor(String colorName, Resources res) {
        return ResourcesCompat.getColor(res, darkColorsId.get(colorName), null);
    }

    public static String getNote(Resources res, String noteKey) {
        int id = notesResidMap.get(noteKey);
        return res.getString(id);
    }

}
