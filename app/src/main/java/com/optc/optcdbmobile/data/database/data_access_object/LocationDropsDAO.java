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

import com.optc.optcdbmobile.data.database.entities.Location;
import com.optc.optcdbmobile.data.database.entities.LocationDrops;
import com.optc.optcdbmobile.data.database.entities.LocationInformation;

import java.util.List;

@Dao
public abstract class LocationDropsDAO implements BaseDAO<LocationDrops> {

    /**
     * @param id unit id
     * @return something
     * @deprecated It's no possible to have stage name information
     */
    @Deprecated
    @Query("SELECT * FROM location_table WHERE id " +
            "IN (SELECT id FROM location_drops_table WHERE unit_id=:id)")
    public abstract List<Location> _getManualDropsOf(int id);


    @Query("SELECT * FROM location_drops_table JOIN location_table ON location_id=id WHERE unit_id=-:id")
    public abstract List<LocationInformation> getManualDropsOf(int id);

    @Query("SELECT * FROM location_drops_table JOIN location_table ON location_id=id " +
            "WHERE unit_id IN " +
            "(SELECT unit_id FROM family_unit_table " +
            "WHERE family_id=(SELECT family_id FROM family_unit_table WHERE unit_id=:id))")
    public abstract List<LocationInformation> getFamilyDropsOf(int id);

    @Query("SELECT COUNT(*) FROM location_drops_table WHERE unit_id=-:id")
    public abstract int getManualDropsCount(int id);

    @Query("SELECT COUNT(*) FROM family_unit_table WHERE unit_id=:id")
    public abstract int getFamilyCount(int id);


}


