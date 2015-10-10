package com.oberig.martovskiy.testtask.net;

import com.oberig.martovskiy.testtask.net.dto.WeatherBody;

import retrofit.http.GET;
import retrofit.http.Query;

public interface OpenWeatherMapApi {

    @GET("/weather")
    WeatherBody getWeather(
            @Query("lat")double latitude,
            @Query("lon")double longitude);

}
