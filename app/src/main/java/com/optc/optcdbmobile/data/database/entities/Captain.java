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

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "captain_table",
        foreignKeys = @ForeignKey(entity = Unit.class,
                parentColumns = {"id"},
                childColumns = {"id"},
                onDelete = ForeignKey.CASCADE)
)
public class Captain {

    @PrimaryKey
    //id equals unit index
    private int id;

    private String notes;

    public Captain(int id, String notes) {
        this.id = id;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public String getNotes() {
        return notes;
    }
}
