package com.optc.optcdbmobile.data.database.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "story_location_table",
        foreignKeys = @ForeignKey(entity = Location.class,
                parentColumns = {"id"},
                childColumns = {"location_id"},
                onDelete = ForeignKey.CASCADE))
public class StoryLocation {

    @PrimaryKey
    @ColumnInfo(name = "location_id")
    private int locationId;
    private String completion;

    public StoryLocation(int locationId, String completion) {
        this.locationId = locationId;
        this.completion = completion;
    }

    public int getLocationId() {
        return locationId;
    }

    public String getCompletion() {
        return completion;
    }
}
