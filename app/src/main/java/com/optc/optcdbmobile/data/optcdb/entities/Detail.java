package com.optc.optcdbmobile.data.optcdb.entities;

import com.optc.optcdbmobile.data.database.entities.Captain;
import com.optc.optcdbmobile.data.database.entities.CaptainDescription;
import com.optc.optcdbmobile.data.database.entities.Limit;
import com.optc.optcdbmobile.data.database.entities.Potential;
import com.optc.optcdbmobile.data.database.entities.PotentialDescription;
import com.optc.optcdbmobile.data.database.entities.Sailor;
import com.optc.optcdbmobile.data.database.entities.SailorDescription;
import com.optc.optcdbmobile.data.database.entities.Special;
import com.optc.optcdbmobile.data.database.entities.SpecialDescription;

import java.util.ArrayList;
import java.util.List;

public class Detail {

    private final List<Captain> captainList = new ArrayList<>();
    private final List<CaptainDescription> captainDescriptionList = new ArrayList<>();
    private final List<Special> specialList = new ArrayList<>();
    private final List<SpecialDescription> specialDescriptionList = new ArrayList<>();
    private final List<Sailor> sailorList = new ArrayList<>();
    private final List<SailorDescription> sailorDescriptionList = new ArrayList<>();
    private final List<Limit> limitList = new ArrayList<>();
    private final List<Potential> potentialList = new ArrayList<>();
    private final List<PotentialDescription> potentialDescriptionList = new ArrayList<>();


    public List<Captain> getCaptainList() {
        return captainList;
    }

    public List<CaptainDescription> getCaptainDescriptionList() {
        return captainDescriptionList;
    }

    public List<Special> getSpecialList() {
        return specialList;
    }

    public List<SpecialDescription> getSpecialDescriptionList() {
        return specialDescriptionList;
    }

    public List<Sailor> getSailorList() {
        return sailorList;
    }

    public List<SailorDescription> getSailorDescriptionList() {
        return sailorDescriptionList;
    }

    public List<Limit> getLimitList() {
        return limitList;
    }

    public List<Potential> getPotentialList() {
        return potentialList;
    }

    public List<PotentialDescription> getPotentialDescriptionList() {
        return potentialDescriptionList;
    }
}
