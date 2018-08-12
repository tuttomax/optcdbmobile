package com.optc.optcdbmobile.data.database.entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "sailor_table",
        foreignKeys = @ForeignKey(entity = Unit.class,
                parentColumns = {"id"},
                childColumns = {"id"},
                onDelete = ForeignKey.CASCADE))
public class Sailor {

    @PrimaryKey
    //id equals unit_id
    private int id;

    public Sailor(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
