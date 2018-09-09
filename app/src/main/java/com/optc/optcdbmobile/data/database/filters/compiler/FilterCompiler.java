package com.optc.optcdbmobile.data.database.filters.compiler;

import com.optc.optcdbmobile.data.database.filters.FilterUI;

import java.util.List;

//TODO Implement this
public abstract class FilterCompiler {
    static final int CAPTAIN_FLAG = 0x1;
    static final int SPECIAL_FLAG = 0x2;
    static final int LIMIT_FLAG = 0x4;
    static final int SAILOR_FLAG = 0x8;
    static final int DROP_FLAG = 0x10;
    static final int TREASURE_FLAG = 0x20;
    static final int COLORS_FLAG = 0x40;
    static final int CLASS1_FLAG = 0x80;
    static final int CLASS2_FLAG = 0x100;
    static final int RARITY_FLAG = 0x200;


    protected final List<FilterUI> list;
    private int flag;

    FilterCompiler(List<FilterUI> list, int flag) {
        this.list = list;
        this.flag = flag;
    }


    public boolean canCompile() {
        return list.size() > 0;
    }

    public boolean canConcatenate(int currentFlag) {
        return flag >= currentFlag && currentFlag > 0;
    }

    public abstract String compile();

    public int getFlag() {
        return flag;
    }

}
