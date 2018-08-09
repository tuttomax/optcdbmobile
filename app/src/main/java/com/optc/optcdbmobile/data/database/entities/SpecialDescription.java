package com.optc.optcdbmobile.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(tableName = "special_description_table",
        primaryKeys = {"special_id", "level"},
        foreignKeys = @ForeignKey(entity = Special.class,
                parentColumns = {"id"},
                childColumns = {"special_id"},
                onDelete = ForeignKey.CASCADE))
public class SpecialDescription {


    @ColumnInfo(name = "special_id")
    private int specialId;
    private int level;
    private String description;
    @ColumnInfo(name = "min_cooldown")
    private int minCooldown;
    @ColumnInfo(name = "max_cooldown")
    private int maxCooldown;

    public SpecialDescription(int specialId, int level, String description, int minCooldown, int maxCooldown) {
        this.specialId = specialId;
        this.level = level;
        this.description = description;
        this.minCooldown = minCooldown;
        this.maxCooldown = maxCooldown;
    }


    public int getSpecialId() {
        return specialId;
    }

    public int getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }

    public int getMinCooldown() {
        return minCooldown;
    }

    public int getMaxCooldown() {
        return maxCooldown;
    }
}
