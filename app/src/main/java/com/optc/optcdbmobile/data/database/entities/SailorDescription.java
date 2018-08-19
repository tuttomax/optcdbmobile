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

@Entity(tableName = "sailor_description_table",
        primaryKeys = {"sailor_id", "level"},
        foreignKeys = @ForeignKey(entity = Sailor.class,
                parentColumns = {"id"},
                childColumns = {"sailor_id"},
                onDelete = ForeignKey.CASCADE))
public class SailorDescription {

    @ColumnInfo(name = "sailor_id")
    private int sailorId;
    private int level;
    private String description;

    public SailorDescription(int sailorId, int level, String description) {
        this.sailorId = sailorId;
        this.level = level;
        this.description = description;
    }

    public int getSailorId() {
        return sailorId;
    }

    public int getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }
}
