/*
 * Copyright 2018 alessandro
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.optc.optcdbmobile.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

/*
   This table will contains all information of drops like:
        id: 1

        name: 'Master'

        index: 0;       (position of unitId in the data array)
        unitId = 10;    (unitId of the character)

        index = 1;
        unitId = 16;

        eccc...

 */
@Entity(tableName = "location_drops_table",
        primaryKeys = {"location_id", "stage_name", "index"},
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
                @Index(value = {"location_id", "stage_name", "index"}, unique = true)
        })
public class LocationDrops {

    @ColumnInfo(name = "location_id")
    private int locationId;
    @NonNull
    @ColumnInfo(name = "stage_name")
    private String stageName;
    private int index;

    @ColumnInfo(name = "unit_id")
    private int unitId;

    public LocationDrops(int locationId, String stageName, int index, int unitId) {
        this.locationId = locationId;
        this.stageName = stageName;
        this.index = index;
        this.unitId = unitId;
    }

    public int getLocationId() {
        return locationId;
    }

    @NonNull
    public String getStageName() {
        return stageName;
    }

    public int getIndex() {
        return index;
    }

    public int getUnitId() {
        return unitId;
    }
}
