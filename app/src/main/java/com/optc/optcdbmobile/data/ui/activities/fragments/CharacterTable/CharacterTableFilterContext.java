package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable;

import com.optc.optcdbmobile.data.database.filters.Filter;
import com.optc.optcdbmobile.data.database.filters.FilterContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class CharacterTableFilterContext implements FilterContext {

    private List<Filter> listColorFilter;

    private List<Filter> listClassFilter;

    private List<Filter> listRarityFilter;

    private List<Filter> listCostFilter;

    private List<Filter> listDropFilter;

    private List<Filter> listExclusionFilter;

    private List<Filter> listTreasureMapFilter;

    private List<Filter> listCaptainFilter;

    private List<Filter> listSpecialFilter;

    private List<Filter> listSailorFilter;

    private List<Filter> listLimitFilter;

    CharacterTableFilterContext() {
        listColorFilter = new ArrayList<>();

        listClassFilter = new ArrayList<>();

        listRarityFilter = new ArrayList<>();

        listCostFilter = new ArrayList<>();

        listDropFilter = new ArrayList<>();

        listExclusionFilter = new ArrayList<>();

        listTreasureMapFilter = new ArrayList<>();

        listCaptainFilter = new ArrayList<>();

        listSpecialFilter = new ArrayList<>();

        listSailorFilter = new ArrayList<>();

        listLimitFilter = new ArrayList<>();
    }


    @Override
    public List<Filter> getColorFilter() {
        return listColorFilter;
    }

    @Override
    public List<Filter> getClassFilter() {
        return listClassFilter;
    }

    @Override
    public List<Filter> getRarityFilter() {
        return listRarityFilter;
    }

    @Override
    public List<Filter> getCostFilter() {
        return listCostFilter;
    }

    @Override
    public List<Filter> getDropFilter() {
        return listDropFilter;
    }

    @Override
    public List<Filter> getExclusionFilter() {
        return listExclusionFilter;
    }

    @Override
    public List<Filter> getTreasureMapFilter() {
        return listTreasureMapFilter;
    }

    @Override
    public List<Filter> getCaptainFilter() {
        return listCaptainFilter;
    }

    @Override
    public List<Filter> getSpecialFilter() {
        return listSpecialFilter;
    }

    @Override
    public List<Filter> getSailorFilter() {
        return listSailorFilter;
    }

    @Override
    public List<Filter> getLimitFilter() {
        return listLimitFilter;
    }

    @Override
    public Filter getQuery() {
        Filter root = Filter.create();

        addGroup(root, listColorFilter);
        concatenate(root, listColorFilter, listClassFilter);
        addGroup(root, listClassFilter);
        concatenate(root, listClassFilter, listRarityFilter);
        addGroup(root, listRarityFilter);
        concatenate(root, listRarityFilter, listCostFilter);
        addGroup(root, listCostFilter);
        concatenate(root, listCostFilter, listDropFilter);
        addGroup(root, listDropFilter);
        concatenate(root, listDropFilter, listExclusionFilter);
        addGroup(root, listExclusionFilter);
        concatenate(root, listExclusionFilter, listTreasureMapFilter);
        addGroup(root, listTreasureMapFilter);
        concatenate(root, listTreasureMapFilter, listCaptainFilter);
        addGroup(root, listCaptainFilter);
        concatenate(root, listCaptainFilter, listSpecialFilter);
        addGroup(root, listSpecialFilter);
        concatenate(root, listSpecialFilter, listLimitFilter);
        addGroup(root, listLimitFilter);
        concatenate(root, listLimitFilter, listSailorFilter);
        addGroup(root, listSailorFilter);

        return root;
    }

    private void addGroup(Filter root, List<Filter> list) {
        if (list.isEmpty()) return;
        Iterator<Filter> colorIterator = list.iterator();
        //root.open();
        root.newCondition(colorIterator.next().build());
        while (colorIterator.hasNext()) {
            root.or().newCondition(colorIterator.next().build());
        }
        //root.close();
    }

    private void concatenate(Filter root, List<Filter> prev, List<Filter> next) {
        if (!prev.isEmpty() && !next.isEmpty()) root.and();
    }
}
