package com.optc.optcdbmobile.data.database.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "special_table",
        foreignKeys =
        @ForeignKey(entity = Unit.class,
                parentColumns = {"id"},
                childColumns = {"id"},
                onDelete = ForeignKey.CASCADE))

public class Special {

    @PrimaryKey
    @ColumnInfo(name = "id")
    //id equals unit_id
    private int id;

    private String name;
    private String notes;

    public Special(int unitId, String name, String notes) {
        this.id = unitId;
        this.name = name;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
    }
}
