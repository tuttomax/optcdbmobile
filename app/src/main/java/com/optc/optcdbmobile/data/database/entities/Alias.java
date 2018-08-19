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

@Entity(tableName = "alias_table",
        primaryKeys = {"id", "index"},
        foreignKeys = @ForeignKey(entity = Unit.class,
                parentColumns = {"id"},
                childColumns = {"id"},
                onDelete = ForeignKey.CASCADE))

public class Alias {


    //id equals unit_id
    private int id;
    private int index;
    private String name;

    public Alias(int id, int index, String name) {
        this.id = id;
        this.index = index;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
