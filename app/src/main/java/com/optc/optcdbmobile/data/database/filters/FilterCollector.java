package com.optc.optcdbmobile.data.database.filters;

import com.optc.optcdbmobile.data.database.filters.creator.CaptainFilterCreator;
import com.optc.optcdbmobile.data.database.filters.creator.ClassFilterCreator;
import com.optc.optcdbmobile.data.database.filters.creator.ColorFilterCreator;
import com.optc.optcdbmobile.data.database.filters.creator.FilterCreator;
import com.optc.optcdbmobile.data.database.filters.creator.TreasureMapFilterCreator;
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


        //TODO Implement regex builder
        //TODO: Implement layout first
        //creator = new RarityFilterCreator(mediator);
        //creator = new CostFilterCreator(mediator);
        creator = new TreasureMapFilterCreator(mediator);
        list.add(creator.getHeader());
        list.add(creator.get("Show Global TM booster", "", new Integer[]{
                2001, 2066, 1968, 2010, 2011, 2008, 2009, 918, 1387, 1388, 1389, 1446, 1447, 1448, 1549, 1550, 1551, 1624, 1970, 1972, 1985, 2000, 2007, 2503, 943, 991, 1175, 1536, 1540, 1577, 1579, 1654, 1665, 1873, 1877, 1906, 1908, 1953, 1955, 1957, 1974, 1987, 1989, 1991, 1993, 1804, 1806, 1849, 1851, 1885, 1887, 1937, 1939, 1222, 1628, 1863, 1976, 1978, 654, 1432, 2015, 1469, 1774, 1918, 1920, 1965, 1966, 1980, 1982, 1808, 1853, 1889, 1916, 1941, 1023, 1245, 1247, 1709, 1711, 1983, 2504
        }));
        list.add(creator.get("Show Japan TM booster", "", new Integer[]{
                2207, 2209, 2181, 2201, 2191, 2203, 2205, 2213, 2148, 2175, 2177, 2183, 2185, 2187, 2189, 2193, 2197, 1593, 1985, 2074, 2099, 2113, 1682, 1684, 1686, 1688, 1753, 1755, 1757, 1759, 1761, 575, 603, 1108, 1696, 1879, 2070, 2097, 1141, 1143, 1899, 2163, 713, 797, 946, 1072, 1095, 1097, 1138, 1215, 1254, 1384, 1397, 1469, 1995, 2046, 2087, 2091, 2144, 1504, 2126, 2137, 2215, 2217
        }));


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

        list.add(creator.get("Universal ATK boosting captains", "of all characters by", "ATK of all characters by"));
        list.add(creator.get("ATK boosting captains", "[Bb]oosts ATK", "[Bb]oosts ATK"));
        list.add(creator.get("HP boosting captains", "[Bb]oosts HP", "[Bb]oosts HP"));
        list.add(creator.get("RCV boosting captains", "[Bb]oosts RCV", new String[]{
                "[Bb]oosts RCV",
                "their RCV"
        }));
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
        list.add(creator.get("Combo Boost Captains", "after the \\d+th hit", "after the [0-9]*th hit"));
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


    //TODO Something better than this
    public String getQuery() {
        Queue<FilterUI> skippedStatment = new ArrayDeque<>();

        List<FilterUI> colorsFilterUI = new ArrayList<>();
        List<FilterUI> classes1FilterUI = new ArrayList<>();
        List<FilterUI> classes2FilterUI = new ArrayList<>();
        List<FilterUI> captainFilterUI = new ArrayList<>();
        List<FilterUI> treasureFilterUI = new ArrayList<>();

        boolean thereIsColors = false;
        boolean thereIsClasses1 = false;
        boolean thereIsClasses2 = false;
        boolean thereIsCaptain = false;
        boolean thereIsTreasure = false;

        StringBuilder finalQuery = new StringBuilder();
        finalQuery.append("SELECT * FROM unit_table WHERE ");

        //Parse filters in different list
        List<FilterUI> selectedFilters = new ArrayList<>();
        for (FilterUI filterUI : list) {
            if (filterUI.isSelected()) {
                if (filterUI.getInfo().getType() == FilterType.CAPTAIN) {
                    captainFilterUI.add(filterUI);
                } else if (filterUI.getInfo().getType() == FilterType.COLOR) {
                    colorsFilterUI.add(filterUI);
                } else if (filterUI.getInfo().getType() == FilterType.CLASS && filterUI.getInfo().getSubtype() == FilterType.Subtype.Class1) {
                    classes1FilterUI.add(filterUI);
                } else if (filterUI.getInfo().getType() == FilterType.CLASS && filterUI.getInfo().getSubtype() == FilterType.Subtype.Class2) {
                    classes2FilterUI.add(filterUI);
                } else if (filterUI.getInfo().getType() == FilterType.TREASURE_MAP) {
                    treasureFilterUI.add(filterUI);
                } else selectedFilters.add(filterUI);
            }
        }

        StringBuilder captainFilter = null;
        {
            if (captainFilterUI.size() > 0) {
                thereIsCaptain = true;
                //Create composite captain filter
                Iterator<FilterUI> captainFilterIterator = captainFilterUI.iterator();
                captainFilter = new StringBuilder();
                captainFilter.append("id IN (SELECT captain_id FROM captain_description_table WHERE ");
                while (captainFilterIterator.hasNext()) {
                    FilterUI filterUI = captainFilterIterator.next();
                    captainFilter.append(filterUI.getInfo().getDatabasePattern());
                    if (captainFilterIterator.hasNext()) captainFilter.append(" AND ");
                    else captainFilter.append(")");
                }
            }
        }

        StringBuilder treasureFilter = null;
        {
            if (treasureFilterUI.size() > 0) {
                thereIsTreasure = true;
                Iterator<FilterUI> treasureFilterIterator = treasureFilterUI.iterator();
                treasureFilter = new StringBuilder();
                while (treasureFilterIterator.hasNext()) {
                    FilterUI filterUI = treasureFilterIterator.next();
                    treasureFilter.append(filterUI.getInfo().getDatabasePattern());
                    //nothing to add
                }
            }
        }


        StringBuilder colorsFilter = null;
        {
            if (colorsFilterUI.size() > 0) {
                thereIsColors = true;
                //Create composite color filter
                Iterator<FilterUI> colorsFilterIterator = colorsFilterUI.iterator();
                colorsFilter = new StringBuilder();
                colorsFilter.append("(");
                while (colorsFilterIterator.hasNext()) {
                    FilterUI filter = colorsFilterIterator.next();
                    colorsFilter.append(filter.getInfo().getDatabasePattern());
                    if (colorsFilterIterator.hasNext()) colorsFilter.append(" OR ");
                }
                colorsFilter.append(")");
            }
        }

        StringBuilder classes1Filter = null;
        {
            if (classes1FilterUI.size() > 0) {
                thereIsClasses1 = true;
                //Create composite class filter
                Iterator<FilterUI> classFilterIterator = classes1FilterUI.iterator();
                classes1Filter = new StringBuilder();
                classes1Filter.append("(");
                while (classFilterIterator.hasNext()) {
                    FilterUI filterUI = classFilterIterator.next();
                    classes1Filter.append(filterUI.getInfo().getDatabasePattern());
                    if (classFilterIterator.hasNext()) classes1Filter.append(" OR ");
                }
                classes1Filter.append(")");
            }
        }
        StringBuilder classes2Filter = null;
        {
            if (classes1FilterUI.size() > 0) {
                thereIsClasses2 = true;
                //Create composite class filter
                Iterator<FilterUI> classFilterIterator = classes2FilterUI.iterator();
                classes2Filter = new StringBuilder();
                classes2Filter.append("(");
                while (classFilterIterator.hasNext()) {
                    FilterUI filterUI = classFilterIterator.next();
                    classes2Filter.append(filterUI.getInfo().getDatabasePattern());
                    if (classFilterIterator.hasNext()) classes2Filter.append(" OR ");
                }
                classes2Filter.append(")");
            }
        }


        if (thereIsCaptain) {
            finalQuery.append(captainFilter.toString());
        }

        if (thereIsTreasure) {
            if (thereIsCaptain) finalQuery.append(" AND");
            finalQuery.append(treasureFilter.toString());
        }

        if (thereIsColors) {
            if (thereIsTreasure) finalQuery.append(" AND ");
            finalQuery.append(colorsFilter.toString());
        }

        if (thereIsClasses1) {
            if (thereIsColors) finalQuery.append(" AND ");
            finalQuery.append(classes1Filter.toString());
        }
        if (thereIsClasses2) {
            if (thereIsClasses1) finalQuery.append(" AND ");
            finalQuery.append(classes2Filter.toString());
        }


        return finalQuery.toString();
    }

    public void clear() {
        for (FilterUI filter : list) {
            filter.setSelected(false, false);
        }
    }
}
