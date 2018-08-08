package com.optc.optcdbmobile.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "booster_evolver_location_table",
        foreignKeys = @ForeignKey(entity = Location.class,
                parentColumns = {"id"},
                childColumns = {"location_id"},
                onDelete = ForeignKey.CASCADE))
public class BoosterEvolverLocation {
    @PrimaryKey
    @ColumnInfo(name = "location_id")
    private int locationId;
    private Byte day;

    public BoosterEvolverLocation(int locationId, Byte day) {
        this.locationId = locationId;
        this.day = day;
    }

    public int getLocationId() {
        return locationId;
    }

    public Byte getDay() {
        return day;
    }
}
