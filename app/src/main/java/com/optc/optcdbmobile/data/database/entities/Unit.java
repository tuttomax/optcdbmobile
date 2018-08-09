package com.optc.optcdbmobile.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "unity_table",
        foreignKeys = {
                @ForeignKey(
                        entity = Family.class,
                        parentColumns = {"id"},
                        childColumns = {"family_id"},
                        onDelete = ForeignKey.SET_NULL)
        },
        indices = @Index(value = "family_id"))

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

    @ColumnInfo(name = "family_id")
    private int familiyId;


    public Unit(int id, String name, String class1, String class2, String type1, String type2, Integer expToMax, Byte levelMax, Integer atkLevel1, Integer maxAtk, Integer hpLevel1, Integer maxHp, Integer rcvLevel1, Integer maxRcv, Byte cost, Byte combo, Byte socket, Float stars, int familiyId) {
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
        this.familiyId = familiyId;
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

    public int getFamiliyId() {
        return familiyId;
    }
}
