package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable;

import android.os.Parcel;
import android.os.Parcelable;

import com.optc.optcdbmobile.data.database.entities.Unit;

public class UnitProxy implements Parcelable {

    public static final Creator<UnitProxy> CREATOR = new Creator<UnitProxy>() {
        @Override
        public UnitProxy createFromParcel(Parcel in) {
            return new UnitProxy(in);
        }

        @Override
        public UnitProxy[] newArray(int size) {
            return new UnitProxy[size];
        }
    };
    private int databaseId;
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

    UnitProxy(Unit unit) {
        databaseId = unit.getId();
        unitId = unit.getUnitId();
        name = unit.getName();
        class1 = unit.getClass1();
        class2 = unit.getClass2();
        type1 = unit.getType1();
        type2 = unit.getType2();
        expToMax = unit.getExpToMax();
        levelMax = unit.getLevelMax();
        atkLevel1 = unit.getAtkLevel1();
        maxAtk = unit.getMaxAtk();
        hpLevel1 = unit.getHpLevel1();
        maxHp = unit.getHpLevel1();
        rcvLevel1 = unit.getRcvLevel1();
        maxRcv = unit.getMaxRcv();
        cost = unit.getCost();
        combo = unit.getCombo();
        socket = unit.getSocket();
        stars = unit.getStars();
    }

    protected UnitProxy(Parcel in) {
        databaseId = in.readInt();
        unitId = in.readInt();
        name = in.readString();
        class1 = in.readString();
        class2 = in.readString();
        type1 = in.readString();
        type2 = in.readString();
        if (in.readByte() == 0) {
            expToMax = null;
        } else {
            expToMax = in.readInt();
        }
        if (in.readByte() == 0) {
            levelMax = null;
        } else {
            levelMax = in.readByte();
        }
        if (in.readByte() == 0) {
            atkLevel1 = null;
        } else {
            atkLevel1 = in.readInt();
        }
        if (in.readByte() == 0) {
            maxAtk = null;
        } else {
            maxAtk = in.readInt();
        }
        if (in.readByte() == 0) {
            hpLevel1 = null;
        } else {
            hpLevel1 = in.readInt();
        }
        if (in.readByte() == 0) {
            maxHp = null;
        } else {
            maxHp = in.readInt();
        }
        if (in.readByte() == 0) {
            rcvLevel1 = null;
        } else {
            rcvLevel1 = in.readInt();
        }
        if (in.readByte() == 0) {
            maxRcv = null;
        } else {
            maxRcv = in.readInt();
        }
        if (in.readByte() == 0) {
            cost = null;
        } else {
            cost = in.readByte();
        }
        if (in.readByte() == 0) {
            combo = null;
        } else {
            combo = in.readByte();
        }
        if (in.readByte() == 0) {
            socket = null;
        } else {
            socket = in.readByte();
        }
        if (in.readByte() == 0) {
            stars = null;
        } else {
            stars = in.readFloat();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(databaseId);
        parcel.writeInt(unitId);
        parcel.writeString(name);
        parcel.writeString(class1);
        parcel.writeString(class2);
        parcel.writeString(type1);
        parcel.writeString(type2);
        if (expToMax == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(expToMax);
        }
        if (levelMax == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeByte(levelMax);
        }
        if (atkLevel1 == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(atkLevel1);
        }
        if (maxAtk == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(maxAtk);
        }
        if (hpLevel1 == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(hpLevel1);
        }
        if (maxHp == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(maxHp);
        }
        if (rcvLevel1 == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(rcvLevel1);
        }
        if (maxRcv == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(maxRcv);
        }
        if (cost == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeByte(cost);
        }
        if (combo == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeByte(combo);
        }
        if (socket == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeByte(socket);
        }
        if (stars == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(stars);
        }
    }


    public int getDatabaseId() {
        return databaseId;
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
}
