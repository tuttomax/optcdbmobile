/*
 * Copyright 2018 alessandro
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
