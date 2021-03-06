package com.optc.optcdbmobile.data.database.filters;

import com.optc.optcdbmobile.data.database.filters.compiler.CaptainFilterCompiler;
import com.optc.optcdbmobile.data.database.filters.compiler.Class1FilterCompiler;
import com.optc.optcdbmobile.data.database.filters.compiler.Class2FilterCompiler;
import com.optc.optcdbmobile.data.database.filters.compiler.ColorFilterCompiler;
import com.optc.optcdbmobile.data.database.filters.compiler.DropFilterCompiler;
import com.optc.optcdbmobile.data.database.filters.compiler.FilterCompiler;
import com.optc.optcdbmobile.data.database.filters.compiler.PotentialFilterCompiler;
import com.optc.optcdbmobile.data.database.filters.compiler.RarityFilterCompiler;
import com.optc.optcdbmobile.data.database.filters.compiler.SailorFilterCompiler;
import com.optc.optcdbmobile.data.database.filters.compiler.SpecialFilterCompiler;
import com.optc.optcdbmobile.data.database.filters.compiler.TreasureFilterCompiler;
import com.optc.optcdbmobile.data.database.filters.creator.CaptainFilterCreator;
import com.optc.optcdbmobile.data.database.filters.creator.ClassFilterCreator;
import com.optc.optcdbmobile.data.database.filters.creator.ColorFilterCreator;
import com.optc.optcdbmobile.data.database.filters.creator.DropFilterCreator;
import com.optc.optcdbmobile.data.database.filters.creator.FilterCreator;
import com.optc.optcdbmobile.data.database.filters.creator.PotentialFilterCreator;
import com.optc.optcdbmobile.data.database.filters.creator.RarityFilterCreator;
import com.optc.optcdbmobile.data.database.filters.creator.SailorFilterCreator;
import com.optc.optcdbmobile.data.database.filters.creator.SpecialFilterCreator;
import com.optc.optcdbmobile.data.database.filters.creator.TreasureMapFilterCreator;
import com.optc.optcdbmobile.data.ui.general.UnitHelper;

import java.util.ArrayDeque;
import java.util.ArrayList;
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


        creator = new RarityFilterCreator(mediator);
        list.add(creator.getHeader());
        list.add(creator.get("1", 1f));
        list.add(creator.get("2", 2f));
        list.add(creator.get("3", 3f));
        list.add(creator.get("4", 4f));
        list.add(creator.get("5", 5f));
        list.add(creator.get("5+", 5.5f));
        list.add(creator.get("6", 6f));
        list.add(creator.get("6+", 6.5f));

        creator = new DropFilterCreator(mediator);
        list.add(creator.getHeader());
        //list.add(creator.get("Farmable", FilterType.Subtype.Farmable, ""));
        //list.add(creator.get("Non Farmable", FilterType.Subtype.Farmable, ""));
        list.add(creator.get("Global Units", FilterType.Subtype.ServerUnit, "global=1"));
        list.add(creator.get("Japan Units", FilterType.Subtype.ServerUnit, "global IS NULL or global=0"));
        list.add(creator.get("In RR Pool", FilterType.Subtype.RRPool, "(rr=1 OR lrr=1)"));
        list.add(creator.get("Not In RR Pool", FilterType.Subtype.RRPool, "(rr=0 OR rr IS NULL OR lrr=0 OR lrr IS NULL)"));
        //list.add(creator.get("Farmable Socket", FilterType.Subtype.FarmableSocket, ""));
        //list.add(creator.get("Non Farmable Socket", FilterType.Subtype.FarmableSocket, ""));

        //TODO: Custom layout
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
                "of ??? characters by"));

        list.add(creator.get("Class-Boosting captains",
                new String[]{
                        "of [FCP]* characters by",
                        "of S[lht]* characters by",
                        "of Dri* characters by"
                }));

        list.add(creator.get("Universal ATK boosting captains", "ATK of all characters by"));
        list.add(creator.get("ATK boosting captains", "[Bb]oosts ATK"));
        list.add(creator.get("HP boosting captains", "[Bb]oosts HP"));
        list.add(creator.get("RCV boosting captains", new String[]{
                "[Bb]oosts RCV",
                "their RCV"
        }));
        list.add(creator.get("Special boosting captains", "[Bb]oosts damage of"));
        list.add(creator.get("2x ATK and HP captains", "[Bb]oosts ATK and HP of*characters by 2x"));
        list.add(creator.get("2x ATK and RCV captains", "[Bb]oosts ATK and RCV of*characters by 2x"));
        list.add(creator.get("2x ATK captains", "[Bb]oosts ATK of*characters by 2x"));
        list.add(creator.get("2.25x ATK captains", "[Bb]oosts ATK of*characters by 2.25x"));
        list.add(creator.get("2.5x ATK captains", "[Bb]oosts ATK of*characters by 2.5x"));
        list.add(creator.get("2.75x ATK captains", "[Bb]oosts ATK of*characters by 2.75x"));
        list.add(creator.get("3x ATK captains", "[Bb]oosts ATK of*characters by 3x"));
        list.add(creator.get("3.25x ATK captains", "[Bb]oosts ATK of*characters by 3.25x"));
        list.add(creator.get("3.5x ATK captains", "[Bb]oosts ATK of*characters by 3.5x"));
        list.add(creator.get("3.75x ATK captains", "[Bb]oosts ATK of*characters by 3.75x"));
        list.add(creator.get("4x ATK captains", "[Bb]oosts ATK of*characters by 4x"));
        list.add(creator.get("4.25x ATK captains", "[Bb]oosts ATK of*characters by 4.25x"));
        list.add(creator.get("HP-based ATK captains", "proportionally to the crew's current HP"));
        list.add(creator.get("Positional captains", "after scoring * in a row"));
        list.add(creator.get("\"Beneficial\" Orb captains", "\"orbs \\\"beneficial\\\"\""));
        list.add(creator.get("Chain multipliers", "[Bb]oosts chain multiplier"));
        list.add(creator.get("Combo Boost Captains", "after the [0-9]*th hit"));
        list.add(creator.get("Cooldown reducers", "[Rr]educes cooldown of all specials"));
        list.add(creator.get("Damage reducers", "[Rr]educes damage received"));
        list.add(creator.get("Healers", "[Rr]ecovers"));
        list.add(creator.get("Tankers", "[Rr]educes damage received by [0-9]*\\% if HP"));
        list.add(creator.get("Zombies", "[Pp]rotects from defeat"));
        list.add(creator.get("End of Turn Damage Dealer", "at the end of each turn"));
        list.add(creator.get("Beli boosters", "amount of Beli"));
        list.add(creator.get("Exp boosters", "amount of EXP"));
        list.add(creator.get("Drop Doublers", "duplicating a drop upon"));
        //endregion

        //region SPECIAL FILTER

        creator = new SpecialFilterCreator(mediator);
        list.add(creator.getHeader());
        list.add(creator.get("Type-boosting specials", "of ??? characters by"));
        list.add(creator.get("Class-boosting specials", new String[]{
                "of [FCP]* characters by",
                "of S[lht]* characters by",
                "of Dri* characters by"
        }));
        list.add(creator.get("Multiple stage specials", "special_id IN (SELECT special_id FROM special_description_table GROUP BY special_id HAVING COUNT(special_id)>1"));
        list.add(creator.get("Universal ATK boosting", "ATK of all characters"));
        list.add(creator.get("1.5x ATK specials", "ATK of * characters by 1.5x"));
        list.add(creator.get("1.75x ATK specials", "ATK of * characters by 1.75x"));
        list.add(creator.get("2x ATK specials", "ATK of * characters by 2x"));
        list.add(creator.get("2.25x ATK specials", "ATK of * characters by 2.25x"));
        list.add(creator.get("Conditional ATK booster", "[bB]oosts ATK against enemies with"));
        list.add(creator.get("Following-turn ATK booster", "[Ii]f during that turn every unit scores a PERFECT hit, boosts ATK of"));
        list.add(creator.get("Color affinity booster", "[bB]oosts the Color Affinity of"));
        list.add(creator.get("Combo boost special", "after [0-9]*th hit in the chain"));
        list.add(creator.get("RCV boost special", "[Bb]oosts the amount healed by RCV"));
        list.add(creator.get("Orb lockers", "locks*orbs for"));
        list.add(creator.get("Orb booster", "[Aa]mplifies the effect of orbs"));
        list.add(creator.get("1.5x orb booster special", "[Aa]mplifies the effect of orbs*by 1.5x for"));
        list.add(creator.get("1.75x orb booster special", "[Aa]mplifies the effect of orbs*by 1.75x for"));
        list.add(creator.get("2x orb booster special", "[Aa]mplifies the effect of orbs by*2x for"));
        list.add(creator.get("2.25x orb booster special", "[Aa]mplifies the effect of orbs*by 2.25x for"));
        list.add(creator.get("Orb chance booster", "Boosts chances of getting * orbs "));
        //list.add(creator.get("Negative to positive orb controllers",""));
        //list.add(creator.get("Orb controllers",""));
        //list.add(creator.get("Full board orb controllers",""));
        list.add(creator.get("[MATCH ALL] Orb controllers", "Changes * orbs into * orbs"));
        list.add(creator.get("Self-Orb controllers", "changes own orb into"));
        list.add(creator.get("Orb randomizer", "[Rr]andomizes all orbs"));
        list.add(creator.get("Orb switchers", "switches orbs between"));
        list.add(creator.get("Orb matchers", new String[]{
                "into [Mm]atching [Oo]rbs",
                "orb into matching"
        }));
        list.add(creator.get("Slot emptiers", "[eE]mpties all slots"));
        list.add(creator.get("\"Beneficial\" orbs enabler", "orbs \"beneficial\""));
        list.add(creator.get("Block orb removers", new String[]{
                "[Cc]hanges*BLOCK",
                "with*BLOCK"
        }));
        list.add(creator.get("Bomb orb removers", new String[]{
                "[Cc]hanges*BOMB",
                "with*BOMB"
        }));
        list.add(creator.get("Delayer", "delay"));
        list.add(creator.get("Damage dealers", "damage to "));
        list.add(creator.get("Single-target damage dealers", "damage to one enemy"));
        list.add(creator.get("Multi-target damage dealers", "damage to all enemies"));
        list.add(creator.get("Area of effect damage dealers", "damage to all enemies"));
        list.add(creator.get("Multi-hit damage dealers", "[Dd]eals [0-9]* hits"));
        list.add(creator.get("Fixed damage dealers", "fixed damage"));
        list.add(creator.get("Typeless damage dealers", "typeless damage to"));
        list.add(creator.get("Typed damage dealers", "in ??? damage to"));
        list.add(creator.get("Additional damage dealers", "as Additional * Damage"));
        list.add(creator.get("Instant defeat special", "instantly defeats"));
        list.add(creator.get("Defense and Barrier Buff  ingnoring damage dealer", "ignore damage negating abilities and barriers"));
        list.add(creator.get("Healers", "[Rr]ecovers * HP"));
        list.add(creator.get("Health reducers", "[Rr]educes crew's current HP"));
        list.add(creator.get("Poisoners", new String[]{
                "[Ii]nflicts Toxic to all enemies",
                "[Pp]oisons all enemies"
        }));
        list.add(creator.get("Poison removers", "[Rr]emoves * Poison"));
        list.add(creator.get("Health cutters", "cuts the current HP of"));
        list.add(creator.get("HP-based damage dealers", "[Dd]eals several times the character's ATK"));
        list.add(creator.get("Defense reducers", "[Rr]educes the defense of"));
        list.add(creator.get("Damage reducers", "[Rr]educes damage received by"));
        list.add(creator.get("Damage nullifiers", "[Rr]educes damage received * by 100%"));
        list.add(creator.get("Bind reducers", "Bind"));
        list.add(creator.get("Despair reducers", "Despair"));
        list.add(creator.get("Silence reducers", "Silence"));
        list.add(creator.get("Paralysis reducers", "Paralysis"));
        list.add(creator.get("Blindness reducers", "Blindness"));
        list.add(creator.get("Crew ATK DOWN reducers", "ATK DOWN"));
        list.add(creator.get("Crew Increased damage taken reducers", "Increase Damage Taken"));
        list.add(creator.get("Crew positive Buff reducers", "all positive buffs"));
        list.add(creator.get("Enemy End of turn Buff reducers", "End of Turn"));
        list.add(creator.get("Enemy Enrage buff reducers", "Enrage"));
        list.add(creator.get("Enemy ATK UP buff reducers", "ATK Up"));
        list.add(creator.get("Enemy Increased defense reducers", "Increased Defense"));
        list.add(creator.get("Enemy Percent damage reduction reducers", "Percent Damage Reduction"));
        list.add(creator.get("Enemy Damage Nullification reducers", "Damage Nullification"));
        list.add(creator.get("Enemy Threshold damage reduction reducers", "Threshold Damage Reduction"));
        list.add(creator.get("Enemy Barrier reducers", "Barrier"));
        list.add(creator.get("Enemy Resilence reducers", "Resilience"));
        list.add(creator.get("Zombies", "[Pp]rotects from defeat"));
        list.add(creator.get("End of Turn Damage dealer", "at the end of the turn"));
        list.add(creator.get("Cooldown reducers", "[rR]educes Special Cooldown"));
        list.add(creator.get("Chain boosters", "to Chain multiplier"));
        list.add(creator.get("Chain lockers", "[lL]ocks the chain"));
        list.add(creator.get("Chain Multiplier Limit reducers", "Chain Multiplier Limit"));
        list.add(creator.get("Chain Coefficient Reduction reducers", "Chain Coefficient Reduction"));

        //endregion


        //region SAILOR

        creator = new SailorFilterCreator(mediator);
        list.add(creator.getHeader());
        list.add(creator.get("Type-boosting sailors", "[Bb]oost base ATK of ???"));
        list.add(creator.get("Class-boosting sailors", new String[]{
                "[Bb]oost base ATK of [FCP]* characters by",
                "[Bb]oost base ATK of S[lht]* characters by",
                "[Bb]oost base ATK of Dri* characters by"
        }));

        list.add(creator.get("ATK boosting sailors", "[Bb]oost * ATK"));
        list.add(creator.get("HP boosting sailors", "[Bb]oost * HP"));
        list.add(creator.get("RCV boosting sailors", "[Bb]oost * RCV"));
        list.add(creator.get("Paralysis reducers", "Reduces Paralysis"));
        list.add(creator.get("Blindness reducers", "Reduces Blindness"));
        list.add(creator.get("Silence reducers", "Reduces Silence"));
        list.add(creator.get("Special Rewind Restores", "when it is rewinded"));
        list.add(creator.get("Special Cooldown Rducers on Special Activation", "reduces own cooldown"));
        list.add(creator.get("Orb retainer", "keep his ??? orb"));
        list.add(creator.get("Blow Away Resistence", "[Bb]lown [Aa]way"));
        list.add(creator.get("Additional Damage Dealer", "Additional Typeless Damage"));
        list.add(creator.get("\"Beneficial\" Orb sailors", "orbs \"beneficial\""));
        list.add(creator.get("STR Orb Team \"Beneficial\" Orb Sailor", "[Mm]akes STR orbs \"beneficial\" for * characters"));
        list.add(creator.get("DEX Orb Team \"Beneficial\" Orb Sailor", "[Mm]akes QCK orbs \"beneficial\" for * characters"));
        list.add(creator.get("QCK Orb Team \"Beneficial\" Orb Sailor", "[Mm]akes DEX orbs \"beneficial\" for * characters"));
        list.add(creator.get("PSY Orb Team \"Beneficial\" Orb Sailor", "[Mm]akes PSY orbs \"beneficial\" for * characters"));
        list.add(creator.get("INT Orb Team \"Beneficial\" Orb Sailor", "[Mm]akes INT orbs \"beneficial\" for * characters"));
        list.add(creator.get("TND Orb Team \"Beneficial\" Orb Sailor", "[Mm]akes TND orbs \"beneficial\" for * characters"));
        list.add(creator.get("RCV Orb Team \"Beneficial\" Orb Sailor", "[Mm]akes RCV orbs \"beneficial\" for * characters"));
        //endregion

        //region LIMIT
        creator = new PotentialFilterCreator(mediator);
        list.add(creator.getHeader());
        list.add(creator.get("Enrage Potential Ability", "Enrage"));
        list.add(creator.get("Reduce No Healing Potential Ability", " Reduce No Healing duration"));
        list.add(creator.get("Critical Hit Potential Ability", "Critical Hit"));
        list.add(creator.get("Slot Bind Self-reduction Potential Ability", "Slot Bind Self-reduction"));
        list.add(creator.get("Barrier Penetration  Potential Ability", "Barrier Penetration"));
        list.add(creator.get("Pinch Healing Potential Ability", "Pinch Healing"));
        list.add(creator.get("Cooldown Reduction Potential Ability", "Cooldown Reduction"));
        list.add(creator.get("Double Special Activation Potential Ability", "Double Special Activation"));
        list.add(creator.get("STR Damage Reduction Potential Ability", "[STR] Damage Reduction"));
        list.add(creator.get("DEX Damage Reduction Potential Ability", "[DEX] Damage Reduction"));
        list.add(creator.get("QCK Damage Reduction Potential Ability", "[QCK] Damage Reduction"));
        list.add(creator.get("PSY Damage Reduction Potential Ability", "[PSY] Damage Reduction"));
        list.add(creator.get("INT Damage Reduction Potential Ability", "[INT] Damage Reduction"));

        //endregion

    }

    public String getQuery() {

        final List<FilterUI> colorFilterList = new ArrayList<>();
        final List<FilterUI> classes1FilterList = new ArrayList<>();
        final List<FilterUI> classes2FilterList = new ArrayList<>();
        final List<FilterUI> captainFilterList = new ArrayList<>();
        final List<FilterUI> treasureFilterList = new ArrayList<>();
        final List<FilterUI> specialFilterList = new ArrayList<>();
        final List<FilterUI> limitFilterList = new ArrayList<>();
        final List<FilterUI> rarityFilterList = new ArrayList<>();
        final List<FilterUI> sailorFilterList = new ArrayList<>();
        final List<FilterUI> dropFilterList = new ArrayList<>();


        for (FilterUI filter : list) {
            if (filter.isSelected()) {
                if (filter.getInfo().getType() == FilterType.CAPTAIN) {
                    captainFilterList.add(filter);
                } else if (filter.getInfo().getType() == FilterType.SPECIAL) {
                    specialFilterList.add(filter);
                } else if (filter.getInfo().getType() == FilterType.COLOR) {
                    colorFilterList.add(filter);
                } else if (filter.getInfo().getType() == FilterType.CLASS && filter.getInfo().getSubtype() == FilterType.Subtype.Class1) {
                    classes1FilterList.add(filter);
                } else if (filter.getInfo().getType() == FilterType.CLASS && filter.getInfo().getSubtype() == FilterType.Subtype.Class2) {
                    classes2FilterList.add(filter);
                } else if (filter.getInfo().getType() == FilterType.TREASURE_MAP) {
                    treasureFilterList.add(filter);
                } else if (filter.getInfo().getType() == FilterType.LIMIT) {
                    limitFilterList.add(filter);
                } else if (filter.getInfo().getType() == FilterType.RARITY) {
                    rarityFilterList.add(filter);
                } else if (filter.getInfo().getType() == FilterType.SAILOR) {
                    sailorFilterList.add(filter);
                } else if (filter.getInfo().getType() == FilterType.DROP) {
                    dropFilterList.add(filter);
                }
            }
        }

        StringBuilder finalQuery = new StringBuilder();
        finalQuery.append("SELECT * FROM unit_table WHERE ");

        Queue<FilterCompiler> compilers = new ArrayDeque<FilterCompiler>() {{

            add(new CaptainFilterCompiler(captainFilterList));
            add(new SpecialFilterCompiler(specialFilterList));
            add(new PotentialFilterCompiler(limitFilterList));
            add(new SailorFilterCompiler(sailorFilterList));
            add(new DropFilterCompiler(dropFilterList));
            //add(new ExclusionFilterCompiler(exclusionFilterList));
            add(new TreasureFilterCompiler(treasureFilterList));
            add(new ColorFilterCompiler(colorFilterList));
            add(new Class1FilterCompiler(classes1FilterList));
            add(new Class2FilterCompiler(classes2FilterList));
            add(new RarityFilterCompiler(rarityFilterList));
        }};

        int currentFlag = 0x0;
        while (!compilers.isEmpty()) {
            FilterCompiler compiler = compilers.remove();
            if (compiler.canCompile()) {
                if (compiler.canConcatenate(currentFlag)) finalQuery.append(" AND ");
                finalQuery.append(compiler.compile());
                currentFlag |= compiler.getFlag();
            }
        }


        return finalQuery.toString();
    }

    public void clear() {
        for (FilterUI filter : list) {
            filter.setSelected(false, false);
        }
    }
}
