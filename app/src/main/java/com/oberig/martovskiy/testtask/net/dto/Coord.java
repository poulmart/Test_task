package com.oberig.martovskiy.testtask.net.dto;

import com.google.gson.annotations.SerializedName;

public class Coord {

    @SerializedName("lon")
    private long lon;
    @SerializedName("lat")
    private double lat;

    public long getLon() {
        return lon;
    }

    public void setLon(long lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

}
