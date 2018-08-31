package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable;

import com.optc.optcdbmobile.data.database.filters.__Filter;
import com.optc.optcdbmobile.data.database.filters.__FilterContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class __CharacterTableFilterContext implements __FilterContext {

    private List<__Filter> listColorFilter;

    private List<__Filter> listClassFilter;

    private List<__Filter> listRarityFilter;

    private List<__Filter> listCostFilter;

    private List<__Filter> listDropFilter;

    private List<__Filter> listExclusionFilter;

    private List<__Filter> listTreasureMapFilter;

    private List<__Filter> listCaptainFilter;

    private List<__Filter> listSpecialFilter;

    private List<__Filter> listSailorFilter;

    private List<__Filter> listLimitFilter;

    __CharacterTableFilterContext() {
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
    public List<__Filter> getColorFilter() {
        return listColorFilter;
    }

    @Override
    public List<__Filter> getClassFilter() {
        return listClassFilter;
    }

    @Override
    public List<__Filter> getRarityFilter() {
        return listRarityFilter;
    }

    @Override
    public List<__Filter> getCostFilter() {
        return listCostFilter;
    }

    @Override
    public List<__Filter> getDropFilter() {
        return listDropFilter;
    }

    @Override
    public List<__Filter> getExclusionFilter() {
        return listExclusionFilter;
    }

    @Override
    public List<__Filter> getTreasureMapFilter() {
        return listTreasureMapFilter;
    }

    @Override
    public List<__Filter> getCaptainFilter() {
        return listCaptainFilter;
    }

    @Override
    public List<__Filter> getSpecialFilter() {
        return listSpecialFilter;
    }

    @Override
    public List<__Filter> getSailorFilter() {
        return listSailorFilter;
    }

    @Override
    public List<__Filter> getLimitFilter() {
        return listLimitFilter;
    }

    @Override
    public __Filter getQuery() {
        __Filter root = __Filter.create();

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

    private void addGroup(__Filter root, List<__Filter> list) {
        if (list.isEmpty()) return;
        Iterator<__Filter> colorIterator = list.iterator();
        //root.open();
        root.newCondition(colorIterator.next().build());
        while (colorIterator.hasNext()) {
            root.or().newCondition(colorIterator.next().build());
        }
        //root.close();
    }

    private void concatenate(__Filter root, List<__Filter> prev, List<__Filter> next) {
        if (!prev.isEmpty() && !next.isEmpty()) root.and();
    }
}
