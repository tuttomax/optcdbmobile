package com.optc.optcdbmobile.data.optcdb.location.entities;

import android.util.SparseArray;

import java.util.List;

@Deprecated
public class BoosterEvolverDrops extends Drops {
    private final SparseArray<List<Integer>> Drops = new SparseArray<>();

    private Byte day;

    public Byte getDay() {
        return day;
    }

    public void setDay(Byte day) {
        this.day = day;
    }

    public SparseArray<List<Integer>> getDrops() {
        return Drops;
    }
}
