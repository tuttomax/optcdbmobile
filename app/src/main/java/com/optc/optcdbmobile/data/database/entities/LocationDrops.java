package com.optc.optcdbmobile.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

/*
   This table will contains all information of drops like:
        id: 1 (autogenerate in Location)

        name: 'Master'

        index: 0;       (position of unitId in the data array)
        unitId = 10;    (unitId of the character)

        index = 1;
        unitId = 16;

        eccc...

 */
@Entity(tableName = "location_drops_table",
        primaryKeys = {"location_id", "name", "index"},
        foreignKeys = {
                @ForeignKey(entity = Location.class,
                        parentColumns = {"id"},
                        childColumns = {"location_id"},
                        onDelete = ForeignKey.CASCADE),

                // I deleted this because drops_table should contains
                //      - skull id
                //      - manual id (negative number)
                /* @ForeignKey(entity = Unit.class,
                        parentColumns = {"id"},
                        childColumns = {"unit_id"},
                        onDelete = ForeignKey.SET_NULL) */
        },

        indices = {
                @Index(value = "location_id"),
                @Index(value = {"location_id", "name", "index"}, unique = true)
        })
public class LocationDrops {

    @ColumnInfo(name = "location_id")
    private int locationId;
    @NonNull
    private String name;
    private int index;

    @ColumnInfo(name = "unit_id")
    private int unitId;

    public LocationDrops(int locationId, String name, int index, int unitId) {
        this.locationId = locationId;
        this.name = name;
        this.index = index;
        this.unitId = unitId;
    }

    public int getLocationId() {
        return locationId;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public int getUnitId() {
        return unitId;
    }
}
