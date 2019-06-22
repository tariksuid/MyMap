package com.example.myapplication.weatherAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


//when it calls first -- > we should
public interface WeatherService {
    //token = API KEY ..
    //
    //
    @GET("/data/2.5/weather")
    Call<WeatherResponse> get (@Query("APPID") String token , @Query("lat")String lat, @Query("lon") String lon);
}
