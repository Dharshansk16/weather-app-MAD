package com.example.weather_app.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit geocodingRetrofit = null;
    private static Retrofit weatherRetrofit = null;

    public static WeatherApiService getGeocodingService() {
        if (geocodingRetrofit == null) {
            geocodingRetrofit = new Retrofit.Builder()
                    .baseUrl("https://geocoding-api.open-meteo.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return geocodingRetrofit.create(WeatherApiService.class);
    }

    public static WeatherApiService getWeatherService() {
        if (weatherRetrofit == null) {
            weatherRetrofit = new Retrofit.Builder()
                    .baseUrl("https://api.open-meteo.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return weatherRetrofit.create(WeatherApiService.class);
    }
}
