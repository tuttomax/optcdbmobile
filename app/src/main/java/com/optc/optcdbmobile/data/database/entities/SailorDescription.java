package com.optc.optcdbmobile.data.database.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(tableName = "sailor_description_table",
        primaryKeys = {"sailor_id", "level"},
        foreignKeys = @ForeignKey(entity = Sailor.class,
                parentColumns = {"id"},
                childColumns = {"sailor_id"},
                onDelete = ForeignKey.CASCADE))
public class SailorDescription {

    @ColumnInfo(name = "sailor_id")
    private int sailorId;
    private int level;
    private String description;

    public SailorDescription(int sailorId, int level, String description) {
        this.sailorId = sailorId;
        this.level = level;
        this.description = description;
    }

    public int getSailorId() {
        return sailorId;
    }

    public int getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }
}
