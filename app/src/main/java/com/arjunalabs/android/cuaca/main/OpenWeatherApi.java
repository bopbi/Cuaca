package com.arjunalabs.android.cuaca.main;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by bobbyadiprabowo on 2/16/15.
 */
public interface OpenWeatherApi {

    @GET("/data/2.5/weather")
    Observable<CurrentWeather> getCurrentWeather(
           @Query("lat") double latitude,
           @Query("lon") double longitude
    );
}
