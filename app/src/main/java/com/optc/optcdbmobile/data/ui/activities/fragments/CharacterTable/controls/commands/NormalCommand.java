package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls.commands;

import com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls.Command;
import com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls.FilterContext;
import com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls.FilterInfo;
import com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls.FilterType;

public class NormalCommand implements Command {

    @Override
    public void execute(FilterInfo filter, FilterContext context) {
        if (filter == null) return;
        if (filter.isHeader()) return;
        if (filter.getType() == FilterType.COLOR) {
            context.getColorFilter().add(filter.getFilter());
        } else if (filter.getType() == FilterType.CLASS) {
            context.getClassFilter().add(filter.getFilter());
        } else if (filter.getType() == FilterType.RARITY) {
            context.getRarityFilter().add(filter.getFilter());
        } else if (filter.getType() == FilterType.COST) {
            context.getCostFilter().add(filter.getFilter());
        } else if (filter.getType() == FilterType.DROP) {
            context.getDropFilter().add(filter.getFilter());
        } else if (filter.getType() == FilterType.EXCLUSION) {
            context.getExclusionFilter().add(filter.getFilter());
        } else if (filter.getType() == FilterType.TREASURE_MAP) {
            context.getTreasureMapFilter().add(filter.getFilter());
        } else if (filter.getType() == FilterType.CAPTAIN) {
            context.getCaptainFilter().add(filter.getFilter());
        } else if (filter.getType() == FilterType.SPECIAL) {
            context.getSpecialFilter().add(filter.getFilter());
        } else if (filter.getType() == FilterType.SAILOR) {
            context.getSailorFilter().add(filter.getFilter());
        } else if (filter.getType() == FilterType.LIMIT) {
            context.getLimitFilter().add(filter.getFilter());
        }

    }

    @Override
    public void rollback(FilterInfo filter, FilterContext context) {
        if (filter == null) return;
        if (filter.isHeader()) return;

        if (filter.getType() == FilterType.COLOR) {
            context.getColorFilter().remove(filter.getFilter());
        } else if (filter.getType() == FilterType.CLASS) {
            context.getClassFilter().remove(filter.getFilter());
        } else if (filter.getType() == FilterType.RARITY) {
            context.getRarityFilter().remove(filter.getFilter());
        } else if (filter.getType() == FilterType.COST) {
            context.getCostFilter().remove(filter.getFilter());
        } else if (filter.getType() == FilterType.DROP) {
            context.getDropFilter().remove(filter.getFilter());
        } else if (filter.getType() == FilterType.EXCLUSION) {
            context.getExclusionFilter().remove(filter.getFilter());
        } else if (filter.getType() == FilterType.TREASURE_MAP) {
            context.getTreasureMapFilter().remove(filter.getFilter());
        } else if (filter.getType() == FilterType.CAPTAIN) {
            context.getCaptainFilter().remove(filter.getFilter());
        } else if (filter.getType() == FilterType.SPECIAL) {
            context.getSpecialFilter().remove(filter.getFilter());
        } else if (filter.getType() == FilterType.SAILOR) {
            context.getSailorFilter().remove(filter.getFilter());
        } else if (filter.getType() == FilterType.LIMIT) {
            context.getLimitFilter().remove(filter.getFilter());
        }

    }
}
