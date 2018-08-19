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

import com.optc.optcdbmobile.data.database.entities.SpecialDescription;

@Dao
public abstract class SpecialDescriptionDAO implements BaseDAO<SpecialDescription> {

    @Query(value = "UPDATE special_description_table SET min_cooldown=:min, max_cooldown=:max WHERE special_id=:id")
    public abstract void updateCooldown(int id, int min, int max);


}
