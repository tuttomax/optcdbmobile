package com.optc.optcdbmobile.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(tableName = "potential_description_table",
        primaryKeys = {"potential_id", "level"},
        foreignKeys = @ForeignKey(entity = Potential.class,
                parentColumns = {"id"},
                childColumns = {"potential_id"},
                onDelete = ForeignKey.CASCADE)
)
public class PotentialDescription {

    @ColumnInfo(name = "potential_id")
    private int potentialId;
    private int level;
    private String description;

    public PotentialDescription(int potentialId, int level, String description) {
        this.potentialId = potentialId;
        this.level = level;
        this.description = description;
    }

    public int getPotentialId() {
        return potentialId;
    }

    public int getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }
}
