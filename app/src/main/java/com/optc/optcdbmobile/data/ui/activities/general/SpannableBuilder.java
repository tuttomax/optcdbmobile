package com.optc.optcdbmobile.data.ui.activities.general;

import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;

/**
 * Fluent api for {@link SpannableStringBuilder}
 */
public class SpannableBuilder {

    public static final StyleSpan BOLD_STYLE = new StyleSpan(Typeface.BOLD);
    public static final StyleSpan ITALIC_STYLE = new StyleSpan(Typeface.ITALIC);

    private final SpannableStringBuilder builder;
    private int lastStart;
    private int lastEnd;

    private SpannableBuilder() {
        builder = new SpannableStringBuilder();
    }

    public static SpannableBuilder create() {
        return new SpannableBuilder();
    }

    public SpannableBuilder append(String text) {
        lastStart = builder.length();
        lastEnd = lastStart + text.length();
        builder.append(text);
        return this;
    }


    public SpannableBuilder newline() {
        append("\n");
        return this;
    }

    public SpannableBuilder tab() {
        append("\t");
        return this;
    }


    public SpannableBuilder addSpan(Object what, int flags) {
        builder.setSpan(what, lastStart, lastEnd, flags);
        return this;
    }

    public SpannableBuilder bold() {
        addSpan(CharacterStyle.wrap(BOLD_STYLE), SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableBuilder italic() {
        addSpan(CharacterStyle.wrap(ITALIC_STYLE), SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableBuilder applyRegex(String text, Pattern pattern) {
        Matcher match = pattern.matcher(text);
        if (!match.find()) return this;
        lastStart = match.start();
        lastEnd = match.end();
        return this;
    }

    public SpannableBuilder foreground(int color) {
        addSpan(new ForegroundColorSpan(color), SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }


    public SpannableBuilder background(int color) {
        addSpan(new BackgroundColorSpan(color), SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }


    public SpannableStringBuilder getInternalBuilder() {
        return builder;
    }

}
