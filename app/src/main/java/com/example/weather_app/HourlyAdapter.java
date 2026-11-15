package com.example.weather_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weather_app.network.WeatherResponse;

import java.util.List;
import java.util.Locale;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder> {

    private final List<String> times;
    private final List<Double> temperatures;
    private final List<Integer> weatherCodes;

    public HourlyAdapter(WeatherResponse.HourlyForecast hourlyForecast) {
        this.times = hourlyForecast.getTime();
        this.temperatures = hourlyForecast.getTemperature();
        this.weatherCodes = hourlyForecast.getWeatherCode();
    }

    @NonNull
    @Override
    public HourlyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hourly_forecast, parent, false);
        return new HourlyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyViewHolder holder, int position) {
        // Open-Meteo returns time in format "2023-10-25T14:00"
        String rawTime = times.get(position);
        String formattedTime = rawTime;
        if (rawTime != null && rawTime.contains("T")) {
            formattedTime = rawTime.split("T")[1];
        }

        holder.tvTime.setText(formattedTime);
        holder.tvTemp.setText(String.format(Locale.getDefault(), "%.0f°C", temperatures.get(position)));

        String iconUrl = getIconUrl(weatherCodes.get(position));
        Glide.with(holder.itemView.getContext())
             .load(iconUrl)
             .error(android.R.drawable.ic_menu_gallery)
             .into(holder.ivIcon);
    }

    @Override
    public int getItemCount() {
        // Return max 24 hours
        return times == null ? 0 : Math.min(times.size(), 24);
    }

    static class HourlyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime, tvTemp;
        ImageView ivIcon;

        public HourlyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvHourlyTime);
            tvTemp = itemView.findViewById(R.id.tvHourlyTemp);
            ivIcon = itemView.findViewById(R.id.ivHourlyIcon);
        }
    }

    private String getIconUrl(int code) {
        String iconCode = "01d";
        if (code == 1 || code == 2 || code == 3) iconCode = "03d";
        else if (code == 45 || code == 48) iconCode = "50d";
        else if (code >= 51 && code <= 67) iconCode = "10d";
        else if (code >= 71 && code <= 77) iconCode = "13d";
        else if (code >= 80 && code <= 82) iconCode = "09d";
        else if (code >= 85 && code <= 86) iconCode = "13d";
        else if (code >= 95 && code <= 99) iconCode = "11d";

        return "https://openweathermap.org/img/wn/" + iconCode + "@2x.png";
    }
}
