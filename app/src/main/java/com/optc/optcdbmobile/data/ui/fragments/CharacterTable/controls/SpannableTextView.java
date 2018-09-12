package com.optc.optcdbmobile.data.ui.fragments.CharacterTable.controls;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.util.AttributeSet;

import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.ui.general.UnitHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class SpannableTextView extends AppCompatTextView {

    //TODO: Create a more complex class with custom drawing

    protected final Pattern orbPattern = Pattern.compile("\\[(\\w+?)\\]");
    protected final Pattern boldPattern = Pattern.compile("<b>(.*?)</b>");
    private final Pattern notePattern = Pattern.compile(getContext().getString(R.string.notes_pattern));

    public SpannableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public abstract void buildText();

    protected String resolveNewLine(String text) {
        return text.replace("<br>", "\n");
    }


    protected String getFormattedNotes(String note) {
        note = resolveNewLine(note);

        Matcher matcher = notePattern.matcher(note);
        while (matcher.find()) {
            String internalData = matcher.group(2);

            List<String> params = new ArrayList<>(Arrays.asList(internalData.split(":")));

            if (params.size() > 0) {
                String noteKey = params.get(0).trim();

                if (!noteKey.isEmpty()) {
                    params.remove(0);
                    String fmt = UnitHelper.getNote(getResources(), noteKey);
                    if ("colorAffinity".equals(noteKey) || ("rewind".equals(noteKey))) {
                        fmt = Html.fromHtml(fmt).toString();
                    }
                    String noteFormatted = String.format(fmt, params.toArray());

                    note = note.replace(matcher.group(1), noteFormatted);
                }
            } else {
                String noteKey = internalData.trim();
                String noteFormatted = UnitHelper.getNote(getResources(), noteKey);
                note = note.replace(matcher.group(1), noteFormatted);
            }
        }


        return note;
    }
}
