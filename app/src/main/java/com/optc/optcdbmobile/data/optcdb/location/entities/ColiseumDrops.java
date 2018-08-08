package com.optc.optcdbmobile.data.optcdb.location.entities;

import android.util.SparseArray;

import java.util.List;

@Deprecated
public class ColiseumDrops extends Drops {

    private final SparseArray<List<Integer>> ALL = new SparseArray<>();
    private String slefty;

    public String getSlefty() {
        return slefty;
    }

    public void setSlefty(String slefty) {
        this.slefty = slefty;
    }

    public SparseArray<List<Integer>> getALL() {
        return ALL;
    }
}
