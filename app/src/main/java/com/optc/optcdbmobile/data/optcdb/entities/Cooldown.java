package com.optc.optcdbmobile.data.optcdb.entities;

public class Cooldown {
    private int id;
    private int min;
    private int max;

    public Cooldown(int id, int min, int max) {
        this.id = id;
        this.min = min;
        this.max = max;
    }

    public int getId() {
        return id;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
