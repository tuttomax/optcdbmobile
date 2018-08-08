package com.optc.optcdbmobile.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(tableName = "captain_description_table",
        primaryKeys = {"captain_id", "level"},
        foreignKeys = @ForeignKey(entity = Captain.class,
                parentColumns = {"id"},
                childColumns = {"captain_id"},
                onDelete = ForeignKey.CASCADE))
public class CaptainDescription {

    @ColumnInfo(name = "captain_id")
    private int captainId;
    private int level;
    private String description;

    public CaptainDescription(int captainId, int level, String description) {
        this.captainId = captainId;
        this.level = level;
        this.description = description;
    }

    public int getCaptainId() {
        return captainId;
    }

    public int getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }
}
