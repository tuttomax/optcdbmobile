package com.optc.optcdbmobile.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(tableName = "challenge_data_location_table",
        primaryKeys = {"location_id", "index"},
        foreignKeys = @ForeignKey(entity = Location.class,
                parentColumns = {"id"},
                childColumns = {"location_id"},
                onDelete = ForeignKey.CASCADE))
public class LocationChallengeData {

    @ColumnInfo(name = "location_id")
    private int locationId;
    private int index;
    private String predicate;
    private String reward;

    public LocationChallengeData(int locationId, int index, String predicate, String reward) {
        this.locationId = locationId;
        this.index = index;
        this.predicate = predicate;
        this.reward = reward;
    }

    public int getLocationId() {
        return locationId;
    }

    public int getIndex() {
        return index;
    }

    public String getPredicate() {
        return predicate;
    }

    public String getReward() {
        return reward;
    }
}
