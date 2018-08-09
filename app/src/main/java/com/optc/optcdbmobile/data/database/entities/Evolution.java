package com.optc.optcdbmobile.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "evolution_table",
        foreignKeys = @ForeignKey(entity = Unit.class,
                parentColumns = {"id"},
                childColumns = {"unit_id"},
                onDelete = ForeignKey.CASCADE),
        indices = @Index(value = "unit_id", unique = true))
public class Evolution {

    @PrimaryKey
    @ColumnInfo(name = "evolution_id")
    private int evolutionId;

    @ColumnInfo(name = "unit_id")
    private int unitId;

    private Integer material1;
    private Integer material2;
    private Integer material3;
    private Integer material4;
    private Integer material5;

    public Evolution(int evolutionId, int unitId, Integer material1, Integer material2, Integer material3, Integer material4, Integer material5) {
        this.evolutionId = evolutionId;
        this.unitId = unitId;
        this.material1 = material1;
        this.material2 = material2;
        this.material3 = material3;
        this.material4 = material4;
        this.material5 = material5;
    }

    public int getEvolutionId() {
        return evolutionId;
    }

    public int getUnitId() {
        return unitId;
    }

    public Integer getMaterial1() {
        return material1;
    }

    public Integer getMaterial2() {
        return material2;
    }

    public Integer getMaterial3() {
        return material3;
    }

    public Integer getMaterial4() {
        return material4;
    }

    public Integer getMaterial5() {
        return material5;
    }
}
