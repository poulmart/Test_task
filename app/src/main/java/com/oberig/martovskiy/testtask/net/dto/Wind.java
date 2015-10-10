package com.oberig.martovskiy.testtask.net.dto;


import com.google.gson.annotations.SerializedName;

public class Wind {

    @SerializedName("speed")
    private double speed;
    @SerializedName("deg")
    private long deg;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public long getDeg() {
        return deg;
    }

    public void setDeg(long deg) {
        this.deg = deg;
    }

}
