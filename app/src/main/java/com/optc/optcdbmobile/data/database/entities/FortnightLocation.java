package com.optc.optcdbmobile.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "fortnight_location_table",
        foreignKeys = @ForeignKey(entity = Location.class,
                parentColumns = {"id"},
                childColumns = {"location_id"},
                onDelete = ForeignKey.CASCADE))
public class FortnightLocation {

    @ColumnInfo(name = "location_id")
    @PrimaryKey
    private int locationId;
    private String condition;
    private String challenge;

    public FortnightLocation(int locationId, String condition, String challenge) {
        this.locationId = locationId;
        this.condition = condition;
        this.challenge = challenge;
    }

    public int getLocationId() {
        return locationId;
    }

    public String getCondition() {
        return condition;
    }

    public String getChallenge() {
        return challenge;
    }
}
