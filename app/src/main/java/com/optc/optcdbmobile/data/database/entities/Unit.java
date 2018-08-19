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

@Entity(tableName = "unit_table")
public class Unit {

    @PrimaryKey
    private int id;

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

    public Unit(int id, String name, String class1, String class2, String type1, String type2, Integer expToMax, Byte levelMax, Integer atkLevel1, Integer maxAtk, Integer hpLevel1, Integer maxHp, Integer rcvLevel1, Integer maxRcv, Byte cost, Byte combo, Byte socket, Float stars) {
        this.id = id;
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

}
