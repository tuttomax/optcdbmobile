package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;

import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.database.entities.Sailor;
import com.optc.optcdbmobile.data.database.entities.SailorDescription;
import com.optc.optcdbmobile.data.ui.activities.general.SpannableBuilder;

import java.util.List;

public class SailorTextView extends android.support.v7.widget.AppCompatTextView {

    private Sailor sailor;
    private List<SailorDescription> sailorDescriptions;

    public SailorTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SailorTextView setSailor(Sailor sailor) {
        this.sailor = sailor;
        return this;
    }

    public SailorTextView setSailorDescriptions(List<SailorDescription> sds) {
        this.sailorDescriptions = sds;
        return this;
    }

    public void buildText() {
        SpannableBuilder builder = SpannableBuilder.create();
        if (sailorDescriptions != null) {
            for (SailorDescription sd : sailorDescriptions) {
                appendSailorDescription(builder, sd);
            }
        }

        setText(builder.getInternalBuilder(), BufferType.SPANNABLE);
    }

    public void appendSailorDescription(SpannableBuilder builder, SailorDescription sd) {
        String levelString = sd.getLevel() == 0 ? "Sailor Ability" : "Limit break Sailor Ability";
        String content = sd.getDescription() == null ? "None" : sd.getDescription();

        builder.append(levelString)
                .bold()
                .foreground(Color.WHITE)
                .background(ResourcesCompat.getColor(getResources(), R.color.secondaryColor, null))
                .append(content).foreground(Color.WHITE)
                .newline();
    }


}
