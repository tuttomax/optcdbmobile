package com.optc.optcdbmobile.data.ui.activities;

import com.optc.optcdbmobile.R;

import java.util.HashMap;
import java.util.Map;

public class UnitColor {

    private final static Map<String, Integer> colorsId = new HashMap<String, Integer>() {{
        put("STR", R.color.colorSTR);
        put("QCK", R.color.colorQCK);
        put("DEX", R.color.colorDEX);
        put("PSY", R.color.colorPSY);
        put("INT", R.color.colorINT);
    }};

    public static int getColorId(String colorName) {
        return colorsId.get(colorName);
    }

    public static String getFormattedString(String type1, String type2) {
        StringBuilder builder = new StringBuilder();
        if (type1 == null && type2 == null) return "No type";
        if (type1 != null) {
            builder.append(type1);
        }
        if (type2 != null) {
            builder.append("/");
            builder.append(type2);
        }
        return builder.toString();
    }


}
