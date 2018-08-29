package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls;

import com.optc.optcdbmobile.data.database.filters.Filter;

import java.util.List;

public interface FilterContext {
    List<Filter> getColorFilter();

    List<Filter> getClassFilter();

    List<Filter> getRarityFilter();

    List<Filter> getCostFilter();

    List<Filter> getDropFilter();

    List<Filter> getExclusionFilter();

    List<Filter> getTreasureMapFilter();

    List<Filter> getCaptainFilter();

    List<Filter> getSpecialFilter();

    List<Filter> getSailorFilter();

    List<Filter> getLimitFilter();

    Filter getQuery();
}
