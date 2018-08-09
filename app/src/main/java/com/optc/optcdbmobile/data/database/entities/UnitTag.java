package com.optc.optcdbmobile.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

@Entity(tableName = "unit_tag_table",
        primaryKeys = {"unit_id", "tag_id"},
        foreignKeys = {
                @ForeignKey(entity = Unit.class,
                        parentColumns = {"id"},
                        childColumns = {"unit_id"},
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Tag.class,
                        parentColumns = {"id"},
                        childColumns = {"tag_id"},
                        onDelete = ForeignKey.SET_NULL)},
        indices = {@Index(value = "unit_id"),
                @Index(value = "tag_id")})

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
