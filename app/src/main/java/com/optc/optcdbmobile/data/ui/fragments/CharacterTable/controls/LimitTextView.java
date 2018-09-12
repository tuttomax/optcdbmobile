package com.optc.optcdbmobile.data.ui.fragments.CharacterTable.controls;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;

import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.database.entities.Limit;
import com.optc.optcdbmobile.data.ui.general.SpannableBuilder;

import java.util.List;

public class LimitTextView extends android.support.v7.widget.AppCompatTextView {
    private List<Limit> limits;

    public LimitTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextColor(Color.WHITE);
    }

    public LimitTextView setLimits(List<Limit> limits) {
        this.limits = limits;
        return this;
    }

    public void buildText() {
        SpannableBuilder builder = SpannableBuilder.create()
                .append("Limit Break").bold().newline();


        for (Limit limit : limits) {
            appendLimit(builder, limit);
        }


        setText(builder.getInternalBuilder(), BufferType.SPANNABLE);
    }

    private void appendLimit(SpannableBuilder builder, Limit limit) {
        int level = limit.getIndex();
        String levelString = String.format("Level %d", level++);
        builder.append(levelString).foreground(Color.WHITE).background(ResourcesCompat.getColor(getResources(), R.color.secondaryColor, null))
                .tab().append(limit.getDescription())
                .newline();
    }
}
