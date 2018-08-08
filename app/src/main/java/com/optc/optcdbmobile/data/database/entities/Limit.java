package com.optc.optcdbmobile.data.database.entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(tableName = "limit_table",
        primaryKeys = {"id", "index"},
        foreignKeys = @ForeignKey(entity = Unit.class,
                parentColumns = {"id"},
                childColumns = {"id"},
                onDelete = ForeignKey.CASCADE))
public class Limit {

    //id equals unit_id
    private int id;

    private int index;

    private String description;

    public Limit(int id, int index, String description) {
        this.id = id;
        this.index = index;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getIndex() {
        return index;
    }

    public String getDescription() {
        return description;
    }
}
