package com.optc.optcdbmobile.data.database.data_access_object;


import android.arch.persistence.room.Insert;

import java.util.List;


public interface BaseDAO<T> {

    @Insert
    void insert(T t);

    @Insert
    void insert(List<T> list);
}
