package com.optc.optcdbmobile.data.database.data_access_object;


import android.arch.persistence.room.Insert;


public interface BaseDAO<T> {

    @Insert
    void insert(T t);


}
