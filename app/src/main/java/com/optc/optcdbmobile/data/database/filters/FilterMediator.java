package com.optc.optcdbmobile.data.database.filters;


public interface FilterMediator {
    void toggleState(FilterUI sender);

    void registerFilterUI(FilterUI instance);

    void unregisterFilterUI(FilterUI instance);


}
