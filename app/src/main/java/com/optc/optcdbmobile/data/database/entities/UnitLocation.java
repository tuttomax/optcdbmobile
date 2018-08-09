package com.optc.optcdbmobile.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;


@Deprecated /*  Use LocationDropsDAO
                This exist cause there is a N-N relationship between Unit and Location */
@Entity(tableName = "unit_location_table",
        primaryKeys = {"location_id", "unit_id"},
        foreignKeys = {
                @ForeignKey(entity = Location.class,
                        parentColumns = {"id"},
                        childColumns = {"location_id"}
                ),
                @ForeignKey(entity = Unit.class,
                        parentColumns = {"id"},
                        childColumns = {"unit_id"})
        })
public class UnitLocation {

    @ColumnInfo(name = "location_id")
    private int locationId;
    @ColumnInfo(name = "unit_id")
    private int unitId;
}
