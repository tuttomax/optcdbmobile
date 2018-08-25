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

package com.optc.optcdbmobile.data.database.data_access_object;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.optc.optcdbmobile.data.database.entities.Unit;

import java.util.List;

@Dao
public abstract class UnitDAO implements BaseDAO<Unit> {


    @Query("SELECT * FROM unit_table")
    public abstract List<Unit> getUnits();

    @Query("SELECT * FROM unit_table WHERE lower(name) LIKE :name")
    public abstract List<Unit> getUnitsWithName(String name);

    @Query("SELECT * FROM unit_table WHERE unit_id IN (SELECT id FROM alias_table WHERE lower(name) LIKE :alias)")
    public abstract List<Unit> getUnitsWithAlias(String alias);

    @Query("SELECT * FROM unit_table WHERE id=:id")
    public abstract Unit getUnit(int id);
}
