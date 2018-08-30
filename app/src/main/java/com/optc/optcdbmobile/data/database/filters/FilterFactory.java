package com.optc.optcdbmobile.data.database.filters;

import android.content.Context;
import android.view.View;

public class FilterFactory {
    private Context context;

    public FilterFactory(Context context) {
        this.context = context;
    }

    public FilterUI buildHeader(int type) {
        return new FilterUI(type);
    }

    public FilterUI buildControl(int type, String label, FilterUIClickListener clickListener, View control) {
        FilterUI filterUI = new FilterUI(type, label);
        filterUI.setClickListener(clickListener);
        filterUI.setControl(control);
        return filterUI;
    }

}
