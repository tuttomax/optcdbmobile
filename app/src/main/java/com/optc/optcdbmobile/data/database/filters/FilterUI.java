package com.optc.optcdbmobile.data.database.filters;

import android.view.View;

public class FilterUI {
    private String label;
    private int fingerprint;
    private View control;
    private FilterInfo info;
    private FilterMediator mediator;

    public FilterUI(int type, String label) {
        this.info = new FilterInfo(type);
        this.label = label;
        this.fingerprint = label.hashCode();
    }

    public void setMediator(FilterMediator mediator) {
        this.mediator = mediator;
        mediator.registerFilterUI(this);
    }

    public void unregisterFromMediator() {
        mediator.unregisterFilterUI(this);
    }


    /**
     * Use for header
     *
     * @param type type containing @see com.optc.optcdbmobile.data.database.filters.FilterType HEADER
     */
    public FilterUI(int type) {
        this(type | FilterType.HEADER, FilterType.name(type));
    }


    public FilterInfo getInfo() {
        return info;
    }

    public void setInfo(FilterInfo info) {
        this.info = info;
    }

    public String getLabel() {
        return label;
    }

    public View getControl() {
        return control;
    }

    public void setControl(View control) {
        this.control = control;
        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info.toggle();
                mediator.toggleState(FilterUI.this);
            }
        });
    }

    public int getFingerprint() {
        return fingerprint;
    }
}
