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

@Entity(tableName = "unit_tag_table",
        primaryKeys = {"unit_id", "tag_id"},
        foreignKeys = @ForeignKey(entity = Unit.class,
                        parentColumns = {"id"},
                        childColumns = {"unit_id"},
                onDelete = ForeignKey.CASCADE)
        /*           @ForeignKey(entity = Tag.class,
                           parentColumns = {"id"},
                           childColumns = {"tag_id"},
                           onDelete = ForeignKey.SET_NULL)},
           indices = {@Index(value = "unit_id"),
                   @Index(value = "tag_id")})*/)
@Deprecated
/**
 * @deprecated
 * Teoretically i have to use this but for implementation
 * i will use only one TAG table which will contains all tags
 */
public class UnitTag {

    @ColumnInfo(name = "unit_id")
    private int unitId;
    @ColumnInfo(name = "tag_id")
    private int tagId;

    public UnitTag(int unitId, int tagId) {
        this.unitId = unitId;
        this.tagId = tagId;
    }

    public int getUnitId() {
        return unitId;
    }

    public int getTagId() {
        return tagId;
    }
}
