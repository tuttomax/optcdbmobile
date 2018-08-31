package com.optc.optcdbmobile.data.database.filters;

import java.util.HashMap;

/**
 * Class to handled change state between filterui of the same type
 * example. Global units and japan exclusive can't be activated at the same time
 */
public class AvoidCollisionMediator implements FilterMediator {


    private HashMap<FilterType.Subtype, FilterUI> fingerprint;

    public AvoidCollisionMediator() {
        fingerprint = new HashMap<>();
    }

    @Override
    public void toggleState(FilterUI sender) {

        FilterInfo info = sender.getInfo();
        if (info.isSelected()) {
            if (fingerprint.containsKey(info.getSubtype())) {
                FilterUI oldFilter = fingerprint.get(info.getSubtype());
                oldFilter.getInfo().setSelected(false);
            }
        }
    }

    @Override
    public void registerFilterUI(FilterUI instance) {
        fingerprint.put(instance.getInfo().getSubtype(), instance);
    }

    @Override
    public void unregisterFilterUI(FilterUI instance) {
        fingerprint.remove(instance.getInfo().getSubtype());
    }
}
