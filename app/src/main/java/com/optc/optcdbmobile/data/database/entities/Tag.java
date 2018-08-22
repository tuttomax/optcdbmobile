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
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "tag_table",
        foreignKeys = @ForeignKey(entity = Unit.class, parentColumns = {"id"}, childColumns = {"unit_id"}),
        indices = @Index(value = "unit_id"))
public class Tag {

    @PrimaryKey
    @ColumnInfo(name = "unit_id")
    private int unitId;

    private Boolean global;

    private Boolean rro;

    @ColumnInfo(name = "rr")
    private Boolean rareRecruit;
    @ColumnInfo(name = "lrr")
    private Boolean limitedRareRecruit;
    private Boolean promo;
    private Boolean shop;
    private Boolean special;


    public Tag(int unitId, Boolean global, Boolean rro, Boolean rareRecruit, Boolean limitedRareRecruit, Boolean promo, Boolean shop, Boolean special) {
        this.unitId = unitId;
        this.global = global;
        this.rro = rro;
        this.rareRecruit = rareRecruit;
        this.limitedRareRecruit = limitedRareRecruit;
        this.promo = promo;
        this.shop = shop;
        this.special = special;
    }

    public int getUnitId() {
        return unitId;
    }

    public Boolean getGlobal() {
        return global;
    }

    public Boolean getRro() {
        return rro;
    }

    public Boolean getRareRecruit() {
        return rareRecruit;
    }

    public Boolean getLimitedRareRecruit() {
        return limitedRareRecruit;
    }

    public Boolean getPromo() {
        return promo;
    }

    public Boolean getShop() {
        return shop;
    }

    public Boolean getSpecial() {
        return special;
    }
}
