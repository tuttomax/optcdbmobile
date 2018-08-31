package com.optc.optcdbmobile.data.database.filters;

public class FilterBehaviour {
    public static final int AND = 1;
    public static final int OR = 2;


    private int behaviourType;
    private int target;
    private int sender;

    /**
     * Define a behaviour between filter type
     *
     * @param behaviourType
     * @param target
     * @param sender
     */
    public FilterBehaviour(int behaviourType, int target, int sender) {
        this.behaviourType = behaviourType;
        this.target = target;
        this.sender = sender;
    }
}
