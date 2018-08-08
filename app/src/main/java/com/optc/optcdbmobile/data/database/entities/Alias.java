package com.optc.optcdbmobile.data.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(tableName = "alias_table",
        primaryKeys = {"id", "index"},
        foreignKeys = @ForeignKey(entity = Unit.class,
                parentColumns = {"id"},
                childColumns = {"id"},
                onDelete = ForeignKey.CASCADE))

public class Alias {


    //id equals unit_id
    private int id;
    private int index;
    private String name;

    public Alias(int id, int index, String name) {
        this.id = id;
        this.index = index;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
