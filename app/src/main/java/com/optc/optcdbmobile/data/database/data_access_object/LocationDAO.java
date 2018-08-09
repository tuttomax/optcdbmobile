package com.optc.optcdbmobile.data.database.data_access_object;

import android.arch.persistence.room.Dao;

import com.optc.optcdbmobile.data.database.entities.Location;

@Dao
public abstract class LocationDAO implements BaseDAO<Location> {
}
