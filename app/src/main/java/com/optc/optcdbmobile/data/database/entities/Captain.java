package com.optc.optcdbmobile.data.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "captain_table",
        foreignKeys = @ForeignKey(entity = Unit.class,
                parentColumns = {"id"},
                childColumns = {"id"},
                onDelete = ForeignKey.CASCADE)
)
public class Captain {

    @PrimaryKey
    //id equals unit_id
    private int id;

    private String notes;

    public Captain(int id, String notes) {
        this.id = id;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public String getNotes() {
        return notes;
    }
}
