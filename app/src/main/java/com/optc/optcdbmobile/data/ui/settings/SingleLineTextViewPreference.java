package com.optc.optcdbmobile.data.ui.settings;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Build;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.optc.optcdbmobile.R;

public class SingleLineTextViewPreference extends Preference {

    private ColorStateList titleColor;
    private ColorStateList summaryColor;
    private int allBackgroundColor;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SingleLineTextViewPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public SingleLineTextViewPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SingleLineTextViewPreference(Context context, AttributeSet attrs) {
        super(context, attrs);


        setShouldDisableView(false);
        setEnabled(false);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SingleLineTextViewPreference);

        titleColor = array.getColorStateList(R.styleable.SingleLineTextViewPreference_titleColor);
        summaryColor = array.getColorStateList(R.styleable.SingleLineTextViewPreference_summaryColor);
        allBackgroundColor = array.getColor(R.styleable.SingleLineTextViewPreference_allBackgroundColor, 0);

        setDefaultValue(getPersistedString(""));

        array.recycle();
    }

    public SingleLineTextViewPreference(Context context) {
        super(context);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.RIGHT_OF, android.R.id.title);

        TextView title = view.findViewById(android.R.id.title);
        TextView summary = view.findViewById(android.R.id.summary);

        summary.setLayoutParams(params);

        int hPadding = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, summary.getResources().getDisplayMetrics()));
        int vPadding = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, summary.getResources().getDisplayMetrics()));

        summary.setPadding(hPadding, vPadding, hPadding, vPadding);

        title.setTextColor(titleColor);
        summary.setTextColor(summaryColor);

        view.setBackgroundColor(allBackgroundColor);
    }


}
