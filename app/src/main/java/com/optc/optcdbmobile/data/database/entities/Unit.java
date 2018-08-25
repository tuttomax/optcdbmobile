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

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "unit_table")
public class Unit implements Comparable<Unit> {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "unit_id")
    private int unitId;

    private String name;

    private String class1;
    private String class2;

    private String type1;
    private String type2;

    private Integer expToMax;
    private Byte levelMax;

    private Integer atkLevel1;
    private Integer maxAtk;

    private Integer hpLevel1;
    private Integer maxHp;

    private Integer rcvLevel1;
    private Integer maxRcv;

    private Byte cost;
    private Byte combo;
    private Byte socket;

    private Float stars;

    public Unit(int id, int unitId, String name, String class1, String class2, String type1, String type2, Integer expToMax, Byte levelMax, Integer atkLevel1, Integer maxAtk, Integer hpLevel1, Integer maxHp, Integer rcvLevel1, Integer maxRcv, Byte cost, Byte combo, Byte socket, Float stars) {
        this.id = id;
        this.unitId = unitId;
        this.name = name;
        this.class1 = class1;
        this.class2 = class2;
        this.type1 = type1;
        this.type2 = type2;
        this.expToMax = expToMax;
        this.levelMax = levelMax;
        this.atkLevel1 = atkLevel1;
        this.maxAtk = maxAtk;
        this.hpLevel1 = hpLevel1;
        this.maxHp = maxHp;
        this.rcvLevel1 = rcvLevel1;
        this.maxRcv = maxRcv;
        this.cost = cost;
        this.combo = combo;
        this.socket = socket;
        this.stars = stars;
    }

    public int getId() {
        return id;
    }

    public int getUnitId() {
        return unitId;
    }

    public String getName() {
        return name;
    }

    public String getClass1() {
        return class1;
    }

    public String getClass2() {
        return class2;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

    public Integer getExpToMax() {
        return expToMax;
    }

    public Byte getLevelMax() {
        return levelMax;
    }

    public Integer getAtkLevel1() {
        return atkLevel1;
    }

    public Integer getMaxAtk() {
        return maxAtk;
    }

    public Integer getHpLevel1() {
        return hpLevel1;
    }

    public Integer getMaxHp() {
        return maxHp;
    }

    public Integer getRcvLevel1() {
        return rcvLevel1;
    }

    public Integer getMaxRcv() {
        return maxRcv;
    }

    public Byte getCost() {
        return cost;
    }

    public Byte getCombo() {
        return combo;
    }

    public Byte getSocket() {
        return socket;
    }

    public Float getStars() {
        return stars;
    }

    @Ignore
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;

        return obj instanceof Unit && this.getUnitId() == ((Unit) obj).getUnitId();
    }

    @Override
    public int compareTo(@NonNull Unit o) {
        if (unitId == o.unitId
                && (name != null && o.name != null && name.equals(o.name))
                && (type1 != null && o.type1 != null && type1.equals(o.type1))
                && (type2 != null && o.type2 != null && type2.equals(o.type2))
                && (maxAtk != null && o.maxAtk != null & maxAtk.equals(o.maxAtk))
                && (maxHp != null && o.maxHp != null & maxHp.equals(o.maxHp))
                && (maxRcv != null && o.maxRcv != null & maxRcv.equals(o.maxRcv))) return 1;

        return 0;
    }
}
