package com.optc.optcdbmobile.data.database.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "treasure_location_table",
        foreignKeys = @ForeignKey(entity = Location.class,
                parentColumns = {"id"},
                childColumns = {"location_id"},
                onDelete = ForeignKey.CASCADE))
public class TreasureLocation {
    @ColumnInfo(name = "location_id")
    @PrimaryKey
    private int locationId;
    private String sea;

    public TreasureLocation(int locationId, String sea) {
        this.locationId = locationId;
        this.sea = sea;
    }

    public int getLocationId() {
        return locationId;
    }

    public String getSea() {
        return sea;
    }
}
