package com.example.myapplication.weatherAPI;

import com.google.gson.annotations.SerializedName;

public class WeatherMain {

@SerializedName("temp")
    double mTemp ;
    @SerializedName("temp_min")

    double  mTmin ;
    @SerializedName("temp_max")

    double mTmax ;

    public double getmTemp() {
        return mTemp;
    }

    public void setmTemp(double mTemp) {
        this.mTemp = mTemp;
    }

    public double getmTmin() {
        return mTmin;
    }

    public void setmTmin(double mTmin) {
        this.mTmin = mTmin;
    }

    public double getmTmax() {
        return mTmax;
    }

    public void setmTmax(double mTmax) {
        this.mTmax = mTmax;
    }
}
