package com.optc.optcdbmobile.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(tableName = "unit_tag_table",
        primaryKeys = {"unitId", "tagId"},
        foreignKeys = {
                @ForeignKey(entity = Unit.class,
                        parentColumns = {"id"},
                        childColumns = {"unit_id"},
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Tag.class,
                        parentColumns = {"id"},
                        childColumns = {"tag_id"},
                        onDelete = ForeignKey.SET_NULL)})

public class UnitTag {

    @ColumnInfo(name = "unit_id")
    private int unitId;
    @ColumnInfo(name = "tag_id")
    private int tagId;

    public UnitTag(int unitId, int tagId) {
        this.unitId = unitId;
        this.tagId = tagId;
    }

    public int getUnitId() {
        return unitId;
    }

    public int getTagId() {
        return tagId;
    }
}
