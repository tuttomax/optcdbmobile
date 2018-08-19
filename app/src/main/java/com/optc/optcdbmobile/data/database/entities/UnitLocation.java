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
