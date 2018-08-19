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
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "special_location_table",
        foreignKeys = @ForeignKey(entity = Location.class,
                parentColumns = {"id"},
                childColumns = {"location_id"},
                onDelete = ForeignKey.CASCADE))
public class SpecialLocation {

    @PrimaryKey
    @ColumnInfo(name = "location_id")
    private int locationId;
    private String condition;
    private String challenge;
    private Boolean showManual;

    public SpecialLocation(int locationId, String condition, String challenge, Boolean showManual) {
        this.locationId = locationId;
        this.condition = condition;
        this.challenge = challenge;
        this.showManual = showManual;
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

    public Boolean getShowManual() {
        return showManual;
    }
}
