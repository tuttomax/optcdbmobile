package com.optc.optcdbmobile.data.ui.activities.general;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import com.optc.optcdbmobile.R;

import java.util.HashMap;
import java.util.Map;

public class UnitHelper {

    public static final String UNIT_PARCELLABLE = "__UNIT__";
    public static final String UNIT_ID = "__id__";
    private final static Map<String, Integer> colorsId = new HashMap<String, Integer>() {{
        put("STR", R.color.colorSTR);
        put("QCK", R.color.colorQCK);
        put("DEX", R.color.colorDEX);
        put("PSY", R.color.colorPSY);
        put("INT", R.color.colorINT);
    }};
    private final static Map<String, Integer> classId = new HashMap<String, Integer>() {{
        put("Fighter", R.drawable.ic_fighter);
        put("Slasher", R.drawable.ic_slasher);
        put("Shooter", R.drawable.ic_shooter);
        put("Striker", R.drawable.ic_striker);
        put("Driven", R.drawable.ic_driven);
        put("Powerhouse", R.drawable.ic_powerhouse);
        put("Free Spirit", R.drawable.ic_free_spirit);
        put("Cerebral", R.drawable.ic_cerebral);
        put("Evolver", R.drawable.ic_evolver);
        put("Booster", R.drawable.ic_booster);

    }};
    public static final int THUMB_WIDTH = 96;
    public static final int THUMB_HEIGHT = 96;
    private static final int CARD_THUMB_WIDTH = 580;
    private static final int CARD_THUMB_HEIGHT = 580;

    public static int getTypeColor(String colorName, Resources res) {
        return ResourcesCompat.getColor(res, colorsId.get(colorName), null);
    }

    public static String getStarsToString(Float stars) {
        if (stars == 5.5f) return "5+";
        else if (stars == 6.5f) return "6+";
        else return String.valueOf(stars.intValue());
    }

    public static Drawable getClassDrawable(String name, Resources res) {
        return ResourcesCompat.getDrawable(res, classId.get(name), null);
    }


}
