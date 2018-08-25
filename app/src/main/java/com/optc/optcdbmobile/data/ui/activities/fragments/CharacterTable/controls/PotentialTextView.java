package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.optc.optcdbmobile.data.database.entities.Potential;
import com.optc.optcdbmobile.data.database.entities.PotentialDescription;
import com.optc.optcdbmobile.data.ui.activities.general.SpannableBuilder;

import java.util.List;
import java.util.regex.Pattern;

public class PotentialTextView extends android.support.v7.widget.AppCompatTextView {

    private final Pattern levelPattern = Pattern.compile("Level %d");
    private List<Potential> potentials;
    private List<PotentialDescription> potentialDescriptions;

    public PotentialTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextColor(Color.WHITE);
    }

    public PotentialTextView setPotentials(List<Potential> potentials) {
        this.potentials = potentials;
        return this;
    }

    public PotentialTextView setPotentialDescriptions(List<PotentialDescription> potentialDescriptions) {
        this.potentialDescriptions = potentialDescriptions;
        return this;
    }

    public void buildText() {
        SpannableBuilder builder = SpannableBuilder.create();
        for (Potential potential : potentials) {
            appendPotential(builder, potential);
        }

        setText(builder.getInternalBuilder(), BufferType.SPANNABLE);
    }

    private void appendPotential(SpannableBuilder builder, Potential potential) {
        int index = potential.getIndex();
        String indexString = String.format("Potential Ability %d", ++index);
        builder.append(indexString).bold().tab()
                .append(potential.getName()).background(Color.WHITE).foreground(Color.BLACK).newline();
        for (PotentialDescription description : potentialDescriptions) {
            if (description.getIndex() == potential.getIndex()) {
                String content = description.getDescription();
                builder.append(content).applyRegex(content, levelPattern).italic().newline();
            }
        }
    }
}
