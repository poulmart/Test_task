package com.oberig.martovskiy.testtask.net.dto;

import com.google.gson.annotations.SerializedName;

public class Clouds {

    @SerializedName("all")
    private long mAll;

    public long getAll() {
        return mAll;
    }

    public void setAll(long all) {
        this.mAll = all;
    }

}
