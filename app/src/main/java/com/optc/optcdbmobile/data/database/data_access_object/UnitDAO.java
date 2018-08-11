package com.optc.optcdbmobile.data.database.data_access_object;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.optc.optcdbmobile.data.database.entities.Unit;

import java.util.List;

@Dao
public abstract class UnitDAO implements BaseDAO<Unit> {

    @Insert
    public abstract void insert(List<Unit> units);

    @Query(value = "UPDATE unit_table SET family_id=:familyId WHERE id=:unitId")
    public abstract void updateFamilyId(int unitId, int familyId);

}
