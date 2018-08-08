package com.optc.optcdbmobile.data.optcdb.location.entities;

import android.util.SparseArray;

import java.util.List;

@Deprecated
public class RaidDrops extends Drops {
    private String slefty;
    private SparseArray<List<Integer>> All = new SparseArray<>();
    private String condition;

    public String getSlefty() {
        return slefty;
    }

    public void setSlefty(String slefty) {
        this.slefty = slefty;
    }

    public SparseArray<List<Integer>> getAll() {
        return All;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
