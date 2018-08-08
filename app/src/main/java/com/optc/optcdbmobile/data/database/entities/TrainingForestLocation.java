package com.optc.optcdbmobile.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "training_location_table",
        foreignKeys = @ForeignKey(entity = Location.class,
                parentColumns = {"id"},
                childColumns = {"location_id"},
                onDelete = ForeignKey.CASCADE))
public class TrainingForestLocation {

    @ColumnInfo(name = "location_id")
    @PrimaryKey
    private int locationId;
    private String slefty;
    private String completion;

    public TrainingForestLocation(int locationId, String slefty, String completion) {
        this.locationId = locationId;
        this.slefty = slefty;
        this.completion = completion;
    }

    public int getLocationId() {
        return locationId;
    }

    public String getSlefty() {
        return slefty;
    }

    public String getCompletion() {
        return completion;
    }
}
