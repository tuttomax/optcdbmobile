package com.optc.optcdbmobile.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

@Entity(tableName = "family_unit_table",
        primaryKeys = {"unit_id", "family_id"},
        foreignKeys = {
                @ForeignKey(entity = Unit.class, parentColumns = {"id"}, childColumns = "unit_id"),
                @ForeignKey(entity = Family.class, parentColumns = {"id"}, childColumns = "family_id"),
        }, indices = {
        @Index(value = "family_id"),
        @Index(value = "unit_id")
})
public class FamilyUnit {


    @ColumnInfo(name = "family_id")
    private int familyId;
    @ColumnInfo(name = "unit_id")
    private int unitId;

    public FamilyUnit(int familyId, int unitId) {
        this.familyId = familyId;
        this.unitId = unitId;
    }

    public int getFamilyId() {
        return familyId;
    }

    public int getUnitId() {
        return unitId;
    }
}
