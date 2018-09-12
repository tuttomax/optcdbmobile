package com.optc.optcdbmobile.data.database.filters;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to handled change state between filterui of the same type
 * example. Global units and japan exclusive can't be activated at the same time
 */
public class AvoidCollisionMediator implements FilterMediator {

    private final List<FilterUI> internalList;
    private Callback callback;

    public AvoidCollisionMediator(List<FilterUI> list) {
        internalList = list;
    }


    @Override
    public void inform(FilterUI sender) {
        if (sender.isSelected()) {

            FilterInfo info = sender.getInfo();
            int type = info.getType();
            FilterType.Subtype subtype = info.getSubtype();

            if (type == FilterType.DROP) {
                switch (subtype) {
                    case Farmable:
                        deselect(sender, FilterType.DROP, FilterType.Subtype.Farmable);
                        break;
                    case ServerUnit:
                        deselect(sender, FilterType.DROP, FilterType.Subtype.ServerUnit);
                    case RRPool:
                        deselect(sender, FilterType.DROP, FilterType.Subtype.RRPool);
                    case FarmableSocket:
                        deselect(sender, FilterType.DROP, FilterType.Subtype.FarmableSocket);
                        break;
                }
            } else if (type == FilterType.TREASURE_MAP) {
                deselect(sender, FilterType.TREASURE_MAP);
            } else if (type == FilterType.CLASS) {
                List<Integer> indices = getIndices(sender);
                if (indices.size() >= 2) {
                    for (Integer index : indices) {
                        internalList.get(index).setSelected(false, true);
                        callback.OnChangedAfterInform(index, FilterUI.PAYLOAD_SELECTED);
                    }
                }
            } else if (type == FilterType.LIMIT) {
                List<Integer> indices = getIndices(sender);
                if (indices.size() >= 3) {
                    for (Integer index : indices) {
                        internalList.get(index).setSelected(false, true);
                        callback.OnChangedAfterInform(index, FilterUI.PAYLOAD_SELECTED);
                    }
                }
            }

        }

    }

    @Override
    public void setCallback(Callback callback) {
        this.callback = callback;
    }


    private void deselect(FilterUI me, int type, FilterType.Subtype subtype) {
        for (int index = 0; index < internalList.size(); index++) {
            FilterUI filterUI = internalList.get(index);
            if (filterUI != me) {


                if (filterUI.isSelected()) {
                    FilterInfo info = filterUI.getInfo();

                    int filterType = info.getType();
                    FilterType.Subtype filterSubtype = info.getSubtype();


                    if (subtype == null) {
                        if (type == filterType) {
                            filterUI.setSelected(false, true);
                            callback.OnChangedAfterInform(index, FilterUI.PAYLOAD_SELECTED);
                        }
                    } else {
                        if (type == filterType && subtype == filterSubtype) {
                            filterUI.setSelected(false, true);
                            callback.OnChangedAfterInform(index, FilterUI.PAYLOAD_SELECTED);
                        }
                    }

                }
            }
        }
    }

    private void deselect(FilterUI me, int type) {
        deselect(me, type, null);
    }

    private List<Integer> getIndices(FilterUI me) {
        final int type = me.getInfo().getType();
        final List<Integer> list = new ArrayList<>();
        for (int index = 0; index < internalList.size(); index++) {
            FilterUI filterUI = internalList.get(index);
            FilterInfo info = filterUI.getInfo();
            if (filterUI != me &&
                    info.getType() == type && filterUI.isSelected()) {
                list.add(index);
            }

        }

        return list;
    }
}
