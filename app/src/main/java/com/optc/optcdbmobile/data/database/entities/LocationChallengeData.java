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
