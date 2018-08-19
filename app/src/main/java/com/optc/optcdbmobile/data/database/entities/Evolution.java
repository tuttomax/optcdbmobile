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

@Entity(tableName = "evolution_table",
        primaryKeys = {"evolution_id", "unit_id", "index"},
        foreignKeys = @ForeignKey(entity = Unit.class,
                parentColumns = {"id"},
                childColumns = {"unit_id"},
                onDelete = ForeignKey.CASCADE),
        indices = @Index(value = {"unit_id", "evolution_id", "index"}, unique = true))
public class Evolution {

    @ColumnInfo(name = "evolution_id")
    private int evolutionId;

    @ColumnInfo(name = "unit_id")
    private int unitId;

    private int index; //Inserted because some unit have same evolutionId and same unitId :(

    private Integer material1;
    private Integer material2;
    private Integer material3;
    private Integer material4;
    private Integer material5;


    public Evolution(int evolutionId, int unitId, int index, Integer material1, Integer material2, Integer material3, Integer material4, Integer material5) {
        this.evolutionId = evolutionId;
        this.unitId = unitId;
        this.index = index;
        this.material1 = material1;
        this.material2 = material2;
        this.material3 = material3;
        this.material4 = material4;
        this.material5 = material5;
    }

    public int getEvolutionId() {
        return evolutionId;
    }

    public int getUnitId() {
        return unitId;
    }

    public int getIndex() {
        return index;
    }

    public Integer getMaterial1() {
        return material1;
    }

    public Integer getMaterial2() {
        return material2;
    }

    public Integer getMaterial3() {
        return material3;
    }

    public Integer getMaterial4() {
        return material4;
    }

    public Integer getMaterial5() {
        return material5;
    }
}
