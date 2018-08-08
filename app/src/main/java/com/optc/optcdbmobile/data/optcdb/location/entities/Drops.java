package com.optc.optcdbmobile.data.optcdb.location.entities;

@Deprecated
public abstract class Drops {

    private String name;
    private Integer thumb;
    private Boolean global;
    private Integer nakama;
    private Integer gamewith;

    public Integer getNakama() {
        return nakama;
    }

    public void setNakama(Integer nakama) {
        this.nakama = nakama;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }

    public Integer getGamewith() {
        return gamewith;
    }

    public void setGamewith(Integer gamewith) {
        this.gamewith = gamewith;
    }

    public Integer getThumb() {
        return thumb;
    }

    public void setThumb(Integer thumb) {
        this.thumb = thumb;
    }

}
