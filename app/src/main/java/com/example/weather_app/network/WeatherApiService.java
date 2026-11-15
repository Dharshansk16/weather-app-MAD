package com.example.weather_app.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET("v1/search")
    Call<GeocodingResponse> searchCity(
            @Query("name") String cityName,
            @Query("count") int count
    );

    @GET("v1/forecast")
    Call<WeatherResponse> getWeather(
            @Query("latitude") double latitude,
            @Query("longitude") double longitude,
            @Query("current") String currentParams,
            @Query("hourly") String hourlyParams
    );
}
