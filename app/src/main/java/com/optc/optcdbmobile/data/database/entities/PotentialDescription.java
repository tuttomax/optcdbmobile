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

@Entity(tableName = "potential_description_table",
        primaryKeys = {"potential_id", "index", "level"},
        foreignKeys = @ForeignKey(entity = Potential.class,
                parentColumns = {"id", "index"},
                childColumns = {"potential_id", "index"},
                onDelete = ForeignKey.CASCADE)
)
public class PotentialDescription {

    @ColumnInfo(name = "potential_id")
    private int potentialId;
    private int index;
    private int level;
    private String description;

    public PotentialDescription(int potentialId, int index, int level, String description) {
        this.potentialId = potentialId;
        this.index = index;
        this.level = level;
        this.description = description;
    }

    public int getPotentialId() {
        return potentialId;
    }

    public int getIndex() {
        return index;
    }

    public int getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }
}
