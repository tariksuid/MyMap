package com.example.myapplication.weatherAPI;

import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("main") // library [ gson  [
    String mMain;
    @SerializedName("description") //something like a take to know where to put the var when we bring json...

    String mDescription ;

    public String getmMain() {
        return mMain;
    }

    public void setmMain(String mMain) {
        this.mMain = mMain;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
