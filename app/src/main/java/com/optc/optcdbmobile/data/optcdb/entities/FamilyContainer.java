package com.optc.optcdbmobile.data.optcdb.entities;

import com.optc.optcdbmobile.data.database.entities.Family;

import java.util.List;

public class FamilyContainer {

    private Family family;
    private List<Integer> unitIds;

    public FamilyContainer(Family family, List<Integer> unitIds) {
        this.family = family;
        this.unitIds = unitIds;
    }

    public Family getFamily() {
        return family;
    }

    public List<Integer> getUnitIds() {
        return unitIds;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public void setUnitIds(List<Integer> unitIds) {
        this.unitIds = unitIds;
    }
}
