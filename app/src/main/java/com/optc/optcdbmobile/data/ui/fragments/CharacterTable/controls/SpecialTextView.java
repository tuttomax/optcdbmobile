package com.optc.optcdbmobile.data.ui.fragments.CharacterTable.controls;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;

import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.database.entities.Special;
import com.optc.optcdbmobile.data.database.entities.SpecialDescription;
import com.optc.optcdbmobile.data.ui.general.SpannableBuilder;

import java.util.List;

public class SpecialTextView extends SpannableTextView {

    private Special special;
    private List<SpecialDescription> specialDescriptions;

    public SpecialTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setTextColor(Color.WHITE);
    }

    public SpecialTextView setSpecial(Special special) {
        this.special = special;
        return this;
    }

    public SpecialTextView setSpecialDescriptions(List<SpecialDescription> a) {
        this.specialDescriptions = a;
        return this;
    }

    public void buildText() {

        SpannableBuilder builder = SpannableBuilder.create()
                .append("Special:").bold().tab();

        if (special != null) {

            if (special.getName() != null) {
                builder.append(special.getName()).bold().background(Color.WHITE).foreground(Color.DKGRAY)
                        .newline();
            }

            if (specialDescriptions != null) {
                for (SpecialDescription description : specialDescriptions) {
                    appendSpecialDecription(builder, description);
                }
            }
            if (special.getNotes() != null) {
                builder.append(getFormattedNotes(special.getNotes())).italic().newline();
            }
        }
        setText(builder.getInternalBuilder(), BufferType.SPANNABLE);
    }

    private void appendSpecialDecription(SpannableBuilder builder, SpecialDescription descr) {

        builder.append(String.format("Stage %d", descr.getLevel() + 1))
                .background(ResourcesCompat.getColor(getResources(), R.color.secondaryColor, null))
                .append(String.format(" (%d -> %d) ", descr.getMinCooldown(), descr.getMaxCooldown()))
                .italic()
                .newline()
                .append(descr.getDescription())
                .newline();
    }

}
