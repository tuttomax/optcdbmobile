package com.optc.optcdbmobile.data.database.entities;

import android.arch.persistence.room.Embedded;

public class LocationInformation {

    @Embedded
    LocationDrops locationDrops;
    @Embedded
    Location location;

    public LocationDrops getLocationDrops() {
        return locationDrops;
    }

    public void setLocationDrops(LocationDrops locationDrops) {
        this.locationDrops = locationDrops;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
