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

@Entity(tableName = "captain_description_table",
        primaryKeys = {"captain_id", "level"},
        foreignKeys = @ForeignKey(entity = Captain.class,
                parentColumns = {"id"},
                childColumns = {"captain_id"},
                onDelete = ForeignKey.CASCADE),
        indices = @Index(value = "captain_id"))
public class CaptainDescription {

    @ColumnInfo(name = "captain_id")
    private int captainId;
    private int level;
    private String description;

    public CaptainDescription(int captainId, int level, String description) {
        this.captainId = captainId;
        this.level = level;
        this.description = description;
    }

    public int getCaptainId() {
        return captainId;
    }

    public int getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }
}
