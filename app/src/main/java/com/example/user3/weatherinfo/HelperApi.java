package com.example.user3.weatherinfo;


import com.google.android.gms.maps.model.LatLng;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by user3 on 24/4/18.
 */

public interface HelperApi {




        String BASE_URL="http://api.openweathermap.org/";

        String BASIC_URL="https://maps.googleapis.com/";
        String BASIC_WEEKLY_URL="http://api.openweathermap.org/";

    @GET("maps/api/directions/json?")
    Call<DirectionInfo>getDirections(@Query("origin") String origin,
                                     @Query("destination") String destination,
                                     @Query("key") String key);



    @GET("data/2.5/weather?")
         Call<ExtraMain> getWeatherInfo(@Query("lat") String latitude,

                                   @Query("lon") String longitude,

                                   @Query("appid") String appid);


    @GET("data/2.5/forecast/?")
    Call<WholeDay> getWholedayWeather(@Query("lat") String latitude,
                                      @Query("lon") String longitude,
                                      @Query("cnt") String count,
                                      @Query("appid") String appid);




}
