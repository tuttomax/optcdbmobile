package com.optc.optcdbmobile.data.database.filters;

public class Filters {
    /**
     * SELECT * FROM unit_table AS UT WHERE :filter
     **/

    //TODO: Move all static Filter inside building function in order to return new object everytime and avoid cloneable interface
    //TODO: and implement a cache strategy for avoiding new instantiation


    public final static String TRUE = "1";
    public final static String FALSE = "0";

    public final static String BOOSTER = "Booster";
    public final static String EVOLVER = "Evolver";
    public final static Filter COLOR1_FILTER = Filter.create().newCondition("type1").equals("%s");
    public final static Filter COLOR2_FILTER = Filter.create().newCondition("type2").equals("%s");
    public final static Filter CLASS1_FILTER = Filter.create().newCondition("class1").equals("%s");
    public final static Filter CLASS2_FILTER = Filter.create().newCondition("class2").equals("%s");
    public final static Filter STARS_FILTER = Filter.create().newCondition("stars").equals("%s");
    public final static Filter COST_FILTER = Filter.create().newCondition("cost").between("%s", "%s");
    public final static Filter GLOBAL_FILTER = Filter.create().exists().open().select("unit_id").from("tag_table")
            .where().newCondition("global").equals(TRUE).close();
    public final static Filter JAPAN_FILTER = Filter.create().exists().open().select("unit_id").from("tag_table")
            .where().newCondition("global").equals(FALSE).close();
    public final static Filter RARE_RECRUIT_FILTER = Filter.create().exists().open().select("unit_id").from("tag_table")
            .where().newCondition("rr").equals(TRUE).or().newCondition("lrr").equals(TRUE).close();
    public final static Filter EXCLUDE_BASE_FORM = Filter.create().not().exists().open().select("unit_id")
            .from("evolution_table").where().newCondition("unit_id").equals("UT.id").close();

    private final static String[] FOODERS = new String[]{
            "%Group%",
            "%Ensign Navy HQ%",
            "%Armed%Unit",
            "%Billions Baroque%",
            "%Millions Baroque%",
            "%Skypiea Guard%",
            "%Skypiea Enforcer%",
            "%Eneru's Elect%",
            "%Adept, Shandian%",
            "%Nomad, Shandian%",
            "%Nomad, Shandian%",
            "%Seaman Navy%",
            "%Major Navy%",
            "%Corporal Navy%",
            "%Hoodlum%Bounty Hunter%",
            "%Black Cat Pirates%",
            "%Arlong crewmember%",
            "%Gunner Master%",
            "%Cannoneer Master%",
            "%Assasin Master%",
            "Female%Pirates%",
            "Giant%Pirates",
            "Soldier Zombie%Shadow%",
            "General Zombie%Shadow",
            "Wild Zombie",
            "Street Punk",
            "Kuja Warriors",
            "%Naginata Corporal",
            "%Naginata Major%",
            "%Rifle Corporal",
            "%Rifle Major%",
            "%Saber Corporal",
            "%Saber Major%",
            "%Bazooka Corporal",
            "%Bazooka Major%",
            "%Knuckle Corporal",
            "%Knuckle Major%",
            "%Strong Soldier Zombie%",
            "%Speedy Soldier Zombie%",
            "%Crafty Soldier Zombie%",
            "%Hate-Filled Soldier Zombie%",
            "%Egotistical Soldier Zombie%",
            "%Powerful General Zombie%",
            "%Sneaky General Zombie%",
            "%Blazing General Zombie%",
            "%Quick-Draw Gunman%",
            "%Scheming Gunman%",
            "%Technical Gunman%",
            "%Quick-Strike Gunman%",
            "%Strong-Arm Gunman%",
            "%Bold Gunman%",
            "%Suppressor Jailer%",
            "%Emergency Jailer%",
            "%Perimeter Jailer%",
            "%Contemplative Guard%",
            "%Action Guard%",
            "%Fishman Guard%",
            "%Fishman Outlaw%",
            "%Punk Hazard Gas Mask Patrol Soldier%",
            "%Punk Hazard Patrol Troop Corps%",
            "%Donquixote Pirates Member%"
    };
    public final static Filter EXCLUDE_FODDER_FILTER = generate_fooder_filter();

    private static Filter generate_fodder_pattern() {
        Filter filter = Filter.create().open();
        for (int index = 0; index < FOODERS.length; index++) {
            if (index != 0) filter.or();
            filter.newCondition("name").like(FOODERS[index]);
        }
        filter.close();
        return filter;
    }

    private static Filter generate_fooder_filter() {
        return Filter.create().open()
                .newCondition("cost").lesser("2").and()
                .open().newCondition("class1").equals(BOOSTER).or().newCondition("class1").equals(EVOLVER).close().close()
                .or().newCondition(generate_fodder_pattern().build());
    }


}
