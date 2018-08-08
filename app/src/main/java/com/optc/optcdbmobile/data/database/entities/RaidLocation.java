package com.optc.optcdbmobile.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "raid_location_table",
        foreignKeys = @ForeignKey(entity = Location.class,
                parentColumns = {"id"},
                childColumns = {"location_id"},
                onDelete = ForeignKey.CASCADE))
public class RaidLocation {

    @PrimaryKey
    @ColumnInfo(name = "location_id")
    private int locationId;

    private String slefty;
    private String condition;


    public RaidLocation(int locationId, String slefty, String condition) {
        this.locationId = locationId;
        this.slefty = slefty;
        this.condition = condition;
    }

    public int getLocationId() {
        return locationId;
    }

    public String getSlefty() {
        return slefty;
    }

    public String getCondition() {
        return condition;
    }
}
