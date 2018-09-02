package com.optc.optcdbmobile.data.database.filters;

import com.optc.optcdbmobile.data.database.filters.creator.CaptainFilterCreator;
import com.optc.optcdbmobile.data.database.filters.creator.ClassFilterCreator;
import com.optc.optcdbmobile.data.database.filters.creator.ColorFilterCreator;
import com.optc.optcdbmobile.data.database.filters.creator.FilterCreator;
import com.optc.optcdbmobile.data.ui.activities.general.UnitHelper;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

/**
 * Clas for containg all filters
 */
public class FilterCollector {

    private final List<FilterUI> list;
    private final FilterMediator mediator;

    public FilterCollector() {
        list = new ArrayList<>();
        mediator = new AvoidCollisionMediator(list);

    }

    public void setCallback(FilterMediator.Callback callback) {
        mediator.setCallback(callback);
    }

    public int size() {
        return list.size();
    }


    public FilterUI get(int index) {
        return list.get(index);
    }

    public void init() {
        FilterCreator creator = new ColorFilterCreator(mediator);

        list.add(creator.getHeader());
        list.add(creator.get(UnitHelper.STR_STRING));
        list.add(creator.get(UnitHelper.DEX_STRING));
        list.add(creator.get(UnitHelper.QCK_STRING));
        list.add(creator.get(UnitHelper.PSY_STRING));
        list.add(creator.get(UnitHelper.INT_STRING));


        creator = new ClassFilterCreator(mediator);
        list.add(creator.getHeader());
        list.add(creator.get(UnitHelper.FIGHTER_STRING, FilterType.Subtype.Class1));
        list.add(creator.get(UnitHelper.SHOOTER_STRING, FilterType.Subtype.Class1));
        list.add(creator.get(UnitHelper.SLASHER_STRING, FilterType.Subtype.Class1));
        list.add(creator.get(UnitHelper.STRIKER_STRING, FilterType.Subtype.Class1));
        list.add(creator.get(UnitHelper.FREE_SPIRIT_STRING, FilterType.Subtype.Class2));
        list.add(creator.get(UnitHelper.CEREBRAL_STRING, FilterType.Subtype.Class2));
        list.add(creator.get(UnitHelper.POWERHOUSE_STRING, FilterType.Subtype.Class2));
        list.add(creator.get(UnitHelper.DRIVEN_STRING, FilterType.Subtype.Class2));


        //TODO: Implement layout first
        //creator = new RarityFilterCreator(mediator);
        //creator = new CostFilterCreator(mediator);
        //creator = new TreasureMapFilterCreator(mediator);

        //TODO Implement regex builder
        //region CAPTAIN FILTERS
        creator = new CaptainFilterCreator(mediator);
        list.add(creator.getHeader());
        list.add(creator.get("Type-Boosting captains",
                "of.+(STR|QCK|DEX|PSY|INT).+characters by",
                "of ??? characters by"));

        list.add(creator.get("Class-Boosting captains",
                "of.+(Fighter|Slasher|Shooter|Striker|Cerebral|Driven|Free Spirit|Powerhouse).+characters by",
                new String[]{
                        "of [FCP]* characters by",
                        "of S[lht]* characters by",
                        "of Dri* characters by"
                }));

        list.add(creator.get("Universal ATK boosting captains", "of all characters by", "of all characters by"));
        list.add(creator.get("ATK boosting captains", "[Bb]oosts ATK", "[Bb]oosts ATK"));
        list.add(creator.get("HP boosting captains", "[Bb]oosts HP", "[Bb]oosts HP"));
        list.add(creator.get("RCV boosting captains", "[Bb]oosts RCV", "[Bb]oosts RCV"));
        list.add(creator.get("Special boosting captains", "[Bb]oosts damage of", "boosts damage of"));
        list.add(creator.get("2x ATK and HP captains", "[Bb]oosts ATK and HP of.+characters by 2x", "[Bb]oosts ATK and HP of*characters by 2x"));
        list.add(creator.get("2x ATK and RCV captains", "[Bb]oosts ATK and RCV of.+characters by 2x", "[Bb]oosts ATK and RCV of*characters by 2x"));
        list.add(creator.get("2x ATK captains", "[Bb]oosts ATK of.+characters by 2x", "[Bb]oosts ATK of*characters by 2x"));
        list.add(creator.get("2.25x ATK captains", "[Bb]oosts ATK of.+characters by 2.25x", "[Bb]oosts ATK of*characters by 2.25x"));
        list.add(creator.get("2.5x ATK captains", "[Bb]oosts ATK of.+characters by 2.5x", "[Bb]oosts ATK of*characters by 2.5x"));
        list.add(creator.get("2.75x ATK captains", "[Bb]oosts ATK of.+characters by 2.75x", "[Bb]oosts ATK of*characters by 2.75x"));
        list.add(creator.get("3x ATK captains", "[Bb]oosts ATK of.+characters by 3x", "[Bb]oosts ATK of*characters by 3x"));
        list.add(creator.get("3.25x ATK captains", "[Bb]oosts ATK of.+characters by 3.25x", "[Bb]oosts ATK of*characters by 3.25x"));
        list.add(creator.get("3.5x ATK captains", "[Bb]oosts ATK of.+characters by 3.5x", "[Bb]oosts ATK of*characters by 3.5x"));
        list.add(creator.get("3.75x ATK captains", "[Bb]oosts ATK of.+characters by 3.75x", "[Bb]oosts ATK of*characters by 3.75x"));
        list.add(creator.get("4x ATK captains", "[Bb]oosts ATK of.+characters by 4x", "[Bb]oosts ATK of*characters by 4x"));
        list.add(creator.get("4.25x ATK captains", "[Bb]oosts ATK of.+characters by 4.25x", "[Bb]oosts ATK of*characters by 4.25x"));
        list.add(creator.get("HP-based ATK captains", "proportionally to the crew's current HP", "proportionally to the crew's current HP"));
        list.add(creator.get("Positional captains", "after scoring .+ in a row", "after scoring * in a row"));
        list.add(creator.get("\"Beneficial\" Orb captains", "orbs \"beneficial\"", "\"orbs \\\"beneficial\\\"\""));
        list.add(creator.get("Chain multipliers", "[Bb]oosts chain multiplier", "[Bb]oosts chain multiplier"));
        list.add(creator.get("Combo [Bb]oost Captains", "after the \\d+th hit", "after the [0-9]*th hit"));
        list.add(creator.get("Cooldown reducers", "[Rr]educes cooldown of all specials", "[Rr]educes cooldown of all specials"));
        list.add(creator.get("Damage reducers", "[Rr]educes damage received", "[Rr]educes damage received"));
        list.add(creator.get("Healers", "[Rr]ecovers", "[Rr]ecovers"));
        list.add(creator.get("Tankers", "[Rr]educes damage received by \\d% if HP", "[Rr]educes damage received by [0-9]*\\% if HP"));
        list.add(creator.get("Zombies", "[Pp]rotects from defeat", "[Pp]rotects from defeat"));
        list.add(creator.get("End of Turn Damage Dealer", "at the end of each turn", "at the end of each turn"));
        list.add(creator.get("Beli boosters", "amount of Beli", "amount of Beli"));
        list.add(creator.get("Exp boosters", "amount of EXP", "amount of EXP"));
        list.add(creator.get("Drop Doublers", "duplicating a drop upon", "duplicating a drop upon"));
        //endregion
    }


    public String getQuery() {
        Queue<FilterUI> skippedStatment = new ArrayDeque<>();

        StringBuilder builder = new StringBuilder();

        builder.append("SELECT * FROM unit_table WHERE ");


        List<FilterUI> selectedFilters = new ArrayList<>();
        for (FilterUI filterUI : list) {
            if (filterUI.isSelected())
                selectedFilters.add(filterUI);
        }

        Iterator<FilterUI> iter = selectedFilters.iterator();


        while (iter.hasNext()) {
            FilterUI filterUI = iter.next();
            if (filterUI.isSelected()) {
                String statment = filterUI.getInfo().getDatabasePattern();
                String beginStatment = statment.substring(0, 7);
                if (beginStatment.contains("EXISTS (") || beginStatment.contains("IN (")) {
                    builder.append(statment);
                    if (iter.hasNext()) builder.append(" AND ");
                } else {
                    skippedStatment.add(filterUI);
                }
            }
        }

        FilterUI data = null;
        int nextType = -1;
        while ((data = skippedStatment.poll()) != null) {

            nextType = skippedStatment.peek() != null ? skippedStatment.peek().getInfo().getType() : -1;

            builder.append(data.getInfo().getDatabasePattern());

            if (nextType != -1) {
                if (nextType == data.getInfo().getType()) builder.append(" OR ");
                else builder.append(") AND ( ");
            } else {
                builder.append(" ) ");
            }
        }

        return builder.toString();
    }

    public void clear() {
        for (FilterUI filter : list) {
            filter.setSelected(false, false);
        }
    }
}
