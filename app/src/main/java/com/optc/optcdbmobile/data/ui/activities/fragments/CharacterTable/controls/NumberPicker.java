package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls;

import android.content.Context;
import android.util.AttributeSet;


public class NumberPicker extends android.widget.NumberPicker {
    public NumberPicker(Context context) {
        super(context);
    }

    public NumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMinValue(attrs.getAttributeIntValue(null, "min", 1));
        setMaxValue(attrs.getAttributeIntValue(null, "max", 99));
        setValue(attrs.getAttributeIntValue(null, "initial", 1));
    }


}