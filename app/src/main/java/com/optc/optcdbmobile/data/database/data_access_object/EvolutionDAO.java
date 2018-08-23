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

import com.optc.optcdbmobile.data.database.entities.Evolution;

import java.util.List;

@Dao
public abstract class EvolutionDAO implements BaseDAO<Evolution> {

    @Query("SELECT COUNT(evolution_id) FROM evolution_table WHERE unit_id=:id")
    public abstract int getCountEvolutions(int id);

    @Query("SELECT COUNT(unit_id) FROM evolution_table WHERE evolution_id=:id")
    public abstract int getCountEvolvesFrom(int id);

    /**
     * @param id Unit id who is the base form
     * @return List of evolutions from unit with id specified
     */
    @Query("SELECT * FROM evolution_table WHERE unit_id=:id")
    public abstract List<Evolution> getEvolvesTo(int id);

    /**
     * @param id Unit id who is the evolution
     * @return List of evolutions to unit with id specified;
     */
    @Query("SELECT * FROM evolution_table WHERE evolution_id=:id")
    public abstract List<Evolution> getEvolvesFrom(int id);
}
