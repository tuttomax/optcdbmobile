package com.optc.optcdbmobile.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(tableName = "potential_description_table",
        primaryKeys = {"potential_id", "index", "level"},
        foreignKeys = @ForeignKey(entity = Potential.class,
                parentColumns = {"id", "index"},
                childColumns = {"potential_id", "index"},
                onDelete = ForeignKey.CASCADE)
)
public class PotentialDescription {

    @ColumnInfo(name = "potential_id")
    private int potentialId;
    private int index;
    private int level;
    private String description;

    public PotentialDescription(int potentialId, int index, int level, String description) {
        this.potentialId = potentialId;
        this.index = index;
        this.level = level;
        this.description = description;
    }

    public int getPotentialId() {
        return potentialId;
    }

    public int getIndex() {
        return index;
    }

    public int getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }
}
