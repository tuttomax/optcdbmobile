package com.optc.optcdbmobile.data.ui.controls.settings;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.optc.optcdbmobile.R;

public class SingleLinePreference extends Preference {

    private ColorStateList titleColor;
    private ColorStateList summaryColor;
    private int backgroundColor;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SingleLinePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public SingleLinePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SingleLinePreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SingleLinePreference);

        titleColor = array.getColorStateList(R.styleable.SingleLinePreference_titleColor);
        summaryColor = array.getColorStateList(R.styleable.SingleLinePreference_summaryColor);
        backgroundColor = array.getColor(R.styleable.SingleLinePreference_backgroundColor, 0);

        setDefaultValue(getPersistedString(""));

        array.recycle();
    }

    public SingleLinePreference(Context context) {
        super(context);
    }


    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);


        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);

        TextView title = (TextView) holder.findViewById(android.R.id.title);
        title.setTextColor(titleColor);

        TextView summary = (TextView) holder.findViewById(android.R.id.summary);
        params.addRule(RelativeLayout.RIGHT_OF, android.R.id.title);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(RelativeLayout.ALIGN_PARENT_END);

        summary.setLayoutParams(params);
        summary.setTextColor(summaryColor);


        holder.itemView.setBackgroundColor(backgroundColor);
    }

    public void setBackgroundColor(@ColorInt int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setSummaryColor(int summaryColor) {
        this.summaryColor = ColorStateList.valueOf(summaryColor);
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = ColorStateList.valueOf(titleColor);
    }

}
