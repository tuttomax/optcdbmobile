/*
 * Copyright 2018 alessandro
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.optc.optcdbmobile.data.ui.activities.fragments.Settings;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.util.TypedValue;
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


        array.recycle();
    }

    public SingleLinePreference(Context context) {
        super(context);
    }


    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        holder.setDividerAllowedAbove(false);
        holder.setDividerAllowedBelow(true);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_BASELINE, android.R.id.title);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        TextView summary = (TextView) holder.findViewById(android.R.id.summary);
        summary.setLayoutParams(params);
        summary.setTextColor(summaryColor);
        summary.setPadding(0, 0,
                Math.round(
                        TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP, 10,
                                summary.getResources().getDisplayMetrics())),
                0);

        TextView title = (TextView) holder.findViewById(android.R.id.title);
        title.setTextColor(titleColor);

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
