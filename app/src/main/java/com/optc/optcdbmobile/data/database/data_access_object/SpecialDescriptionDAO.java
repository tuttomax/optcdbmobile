package com.optc.optcdbmobile.data.database.data_access_object;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.optc.optcdbmobile.data.database.entities.SpecialDescription;

@Dao
public abstract class SpecialDescriptionDAO implements BaseDAO<SpecialDescription> {

    @Query(value = "UPDATE special_description_table SET min_cooldown=:min, max_cooldown=:max WHERE special_id=:id")
    public abstract void updateCooldown(int id, int min, int max);


}
