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

@Entity(tableName = "family_unit_table",
        primaryKeys = {"unit_id", "family_id"},
        foreignKeys = {
                @ForeignKey(entity = Unit.class, parentColumns = {"id"}, childColumns = "unit_id"),
                @ForeignKey(entity = Family.class, parentColumns = {"id"}, childColumns = "family_id"),
        }, indices = {
        @Index(value = "family_id"),
        @Index(value = "unit_id")
})
public class FamilyUnit {


    @ColumnInfo(name = "family_id")
    private int familyId;
    @ColumnInfo(name = "unit_id")
    private int unitId;

    public FamilyUnit(int familyId, int unitId) {
        this.familyId = familyId;
        this.unitId = unitId;
    }

    public int getFamilyId() {
        return familyId;
    }

    public int getUnitId() {
        return unitId;
    }
}
