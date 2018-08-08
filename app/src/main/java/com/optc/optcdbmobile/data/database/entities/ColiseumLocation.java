package com.optc.optcdbmobile.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "coliseum_location_table",
        foreignKeys = @ForeignKey(entity = Location.class,
                parentColumns = {"id"},
                childColumns = {"location_id"},
                onDelete = ForeignKey.CASCADE))
public class ColiseumLocation {

    @ColumnInfo(name = "location_id")
    @PrimaryKey
    private int locationId;
    private String slefty;

    public ColiseumLocation(int locationId, String slefty) {
        this.locationId = locationId;
        this.slefty = slefty;
    }

    public int getLocationId() {
        return locationId;
    }

    public String getSlefty() {
        return slefty;
    }
}
