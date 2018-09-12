package com.optc.optcdbmobile.data.ui.fragments.CharacterTable.controls;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;

import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.database.entities.Captain;
import com.optc.optcdbmobile.data.database.entities.CaptainDescription;
import com.optc.optcdbmobile.data.ui.general.SpannableBuilder;

import java.util.List;

public class CaptainTextView extends SpannableTextView {

    private Captain captain;
    private List<CaptainDescription> captainDescriptions;

    public CaptainTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CaptainTextView setCaptain(Captain captain) {
        this.captain = captain;
        return this;
    }

    public CaptainTextView setCaptainDescription(List<CaptainDescription> descriptions) {
        this.captainDescriptions = descriptions;
        return this;
    }

    public void buildText() {
        SpannableBuilder builder = SpannableBuilder.create()
                .append("Captain").bold().newline();
        if (captain != null) {

            if (captainDescriptions != null) {
                for (CaptainDescription captainDescription : captainDescriptions) {
                    appendCaptainDescription(builder, captainDescription);
                }
            }

            if (captain.getNotes() != null) {
                builder.append(getFormattedNotes(captain.getNotes())).italic();
            }
        }
        setText(builder.getInternalBuilder(), BufferType.SPANNABLE);

    }


    private void appendCaptainDescription(SpannableBuilder builder, CaptainDescription descr) {
        String levelString = descr.getLevel() == 0
                ? "Base captain ability"
                : String.format("Limit break %d captain ability", descr.getLevel());

        builder.append(levelString).background(ResourcesCompat.getColor(getResources(),
                R.color.secondaryColor, null))
                .append(resolveNewLine(descr.getDescription()))
                .newline();
    }

}
