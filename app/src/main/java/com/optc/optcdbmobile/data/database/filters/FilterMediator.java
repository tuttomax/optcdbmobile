package com.optc.optcdbmobile.data.database.filters;


public interface FilterMediator {
    void inform(FilterUI sender);

    void setCallback(Callback callback);

    interface Callback {
        void OnChangedAfterInform(int index, Object payload);
    }
}
