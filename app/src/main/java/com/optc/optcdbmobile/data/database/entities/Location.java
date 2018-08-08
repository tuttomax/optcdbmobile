package com.optc.optcdbmobile.data.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "location_table")
public class Location {

    @PrimaryKey
    private int id;
    private byte type;

    private String name;
    private Integer thumb;
    private Boolean global;
    private Integer gamewith;
    private Integer nakama;

    public Location(int id, String name, byte type, Integer thumb, Boolean global, Integer gamewith, Integer nakama) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.thumb = thumb;
        this.global = global;
        this.gamewith = gamewith;
        this.nakama = nakama;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte getType() {
        return type;
    }

    public Integer getThumb() {
        return thumb;
    }

    public Boolean getGlobal() {
        return global;
    }

    public Integer getGamewith() {
        return gamewith;
    }

    public Integer getNakama() {
        return nakama;
    }
}
