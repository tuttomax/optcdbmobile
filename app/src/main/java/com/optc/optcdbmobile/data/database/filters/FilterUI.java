package com.optc.optcdbmobile.data.database.filters;

public class FilterUI {

    public static final int PAYLOAD_SELECTED = 1;

    private String label;
    private FilterInfo info;
    private boolean selected;
    private FilterMediator mediator;

    public FilterUI(int type, FilterType.Subtype subtype, String label) {
        this.info = new FilterInfo(type, subtype);
        this.label = label;
    }

    public FilterUI(int type, String label) {
        this.info = new FilterInfo(type);
        this.label = label;
    }

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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected, boolean fromMediator) {
        this.selected = selected;
        if (!fromMediator) mediator.inform(this);
    }

    public void setMediator(FilterMediator mediator) {
        this.mediator = mediator;
    }


}
