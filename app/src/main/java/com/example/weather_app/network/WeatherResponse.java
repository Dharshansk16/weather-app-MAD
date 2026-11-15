package com.example.weather_app.network;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WeatherResponse {
    @SerializedName("current")
    private CurrentWeather current;

    @SerializedName("hourly")
    private HourlyForecast hourly;

    public CurrentWeather getCurrent() {
        return current;
    }

    public HourlyForecast getHourly() {
        return hourly;
    }

    public static class CurrentWeather {
        @SerializedName("temperature_2m")
        private double temperature;

        @SerializedName("apparent_temperature")
        private double apparentTemperature;

        @SerializedName("weather_code")
        private int weatherCode;

        @SerializedName("wind_speed_10m")
        private double windSpeed;

        @SerializedName("relative_humidity_2m")
        private int humidity;

        @SerializedName("uv_index")
        private double uvIndex;

        public double getTemperature() {
            return temperature;
        }

        public double getApparentTemperature() {
            return apparentTemperature;
        }

        public int getWeatherCode() {
            return weatherCode;
        }

        public double getWindSpeed() {
            return windSpeed;
        }

        public int getHumidity() {
            return humidity;
        }

        public double getUvIndex() {
            return uvIndex;
        }
    }

    public static class HourlyForecast {
        @SerializedName("time")
        private List<String> time;

        @SerializedName("temperature_2m")
        private List<Double> temperature;

        @SerializedName("weather_code")
        private List<Integer> weatherCode;

        public List<String> getTime() {
            return time;
        }

        public List<Double> getTemperature() {
            return temperature;
        }

        public List<Integer> getWeatherCode() {
            return weatherCode;
        }
    }
}

