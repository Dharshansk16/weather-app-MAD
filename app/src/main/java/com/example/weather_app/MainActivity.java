package com.example.weather_app;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.weather_app.network.GeocodingResponse;
import com.example.weather_app.network.RetrofitClient;
import com.example.weather_app.network.WeatherResponse;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etSearchCity;
    private ImageButton btnSearch;
    private TextView tvCityName, tvTemperature, tvDescription, tvWindSpeed, tvHumidity, tvFeelsLike, tvUvIndex;
    private ImageView ivWeatherIcon;
    private androidx.recyclerview.widget.RecyclerView rvHourlyForecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupListeners();
        
        // Load default city
        fetchCoordinates("London");
    }

    private void initViews() {
        etSearchCity = findViewById(R.id.etSearchCity);
        btnSearch = findViewById(R.id.btnSearch);
        tvCityName = findViewById(R.id.tvCityName);
        tvTemperature = findViewById(R.id.tvTemperature);
        tvDescription = findViewById(R.id.tvDescription);
        tvWindSpeed = findViewById(R.id.tvWindSpeed);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvFeelsLike = findViewById(R.id.tvFeelsLike);
        tvUvIndex = findViewById(R.id.tvUvIndex);
        ivWeatherIcon = findViewById(R.id.ivWeatherIcon);
        
        rvHourlyForecast = findViewById(R.id.rvHourlyForecast);
        rvHourlyForecast.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false));
    }

    private void setupListeners() {
        btnSearch.setOnClickListener(v -> performSearch());

        etSearchCity.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch();
                return true;
            }
            return false;
        });
    }

    private void performSearch() {
        String query = etSearchCity.getText().toString().trim();
        if (!query.isEmpty()) {
            fetchCoordinates(query);
            hideKeyboard();
        } else {
            Toast.makeText(this, "Please enter a city name", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchCoordinates(String cityName) {
        RetrofitClient.getGeocodingService().searchCity(cityName, 1).enqueue(new Callback<GeocodingResponse>() {
            @Override
            public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GeocodingResponse geocodingResponse = response.body();
                    if (geocodingResponse.getResults() != null && !geocodingResponse.getResults().isEmpty()) {
                        GeocodingResponse.Location location = geocodingResponse.getResults().get(0);
                        tvCityName.setText(location.getName());
                        fetchWeather(location.getLatitude(), location.getLongitude());
                    } else {
                        Toast.makeText(MainActivity.this, "City not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Error fetching city", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GeocodingResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchWeather(double lat, double lon) {
        String currentParams = "temperature_2m,apparent_temperature,weather_code,wind_speed_10m,relative_humidity_2m,uv_index";
        String hourlyParams = "temperature_2m,weather_code";
        RetrofitClient.getWeatherService().getWeather(lat, lon, currentParams, hourlyParams).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weatherResponse = response.body();
                    updateUI(weatherResponse.getCurrent());
                    
                    if(weatherResponse.getHourly() != null) {
                        HourlyAdapter adapter = new HourlyAdapter(weatherResponse.getHourly());
                        rvHourlyForecast.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Error fetching weather", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(WeatherResponse.CurrentWeather weather) {
        tvTemperature.setText(String.format(Locale.getDefault(), "%.1f°C", weather.getTemperature()));
        tvWindSpeed.setText(String.format(Locale.getDefault(), "%.1f km/h", weather.getWindSpeed()));
        tvHumidity.setText(String.format(Locale.getDefault(), "%d %%", weather.getHumidity()));
        tvFeelsLike.setText(String.format(Locale.getDefault(), "%.1f°C", weather.getApparentTemperature()));
        tvUvIndex.setText(String.format(Locale.getDefault(), "%.1f", weather.getUvIndex()));
        
        tvDescription.setText(getWeatherDescription(weather.getWeatherCode()));
        
        String iconUrl = getIconUrl(weather.getWeatherCode());
        Glide.with(this)
             .load(iconUrl)
             .error(android.R.drawable.ic_menu_gallery)
             .into(ivWeatherIcon);
    }

    private String getWeatherDescription(int code) {
        if (code == 0) return "Clear Sky";
        if (code == 1 || code == 2 || code == 3) return "Cloudy";
        if (code == 45 || code == 48) return "Fog";
        if (code >= 51 && code <= 57) return "Drizzle";
        if (code >= 61 && code <= 67) return "Rain";
        if (code >= 71 && code <= 77) return "Snow";
        if (code >= 80 && code <= 82) return "Showers";
        if (code >= 85 && code <= 86) return "Snow Showers";
        if (code >= 95 && code <= 99) return "Thunderstorm";
        return "Unknown";
    }
    
    private String getIconUrl(int code) {
        String iconCode = "01d"; // clear
        if (code == 1 || code == 2 || code == 3) iconCode = "03d"; // cloudy
        else if (code == 45 || code == 48) iconCode = "50d"; // fog
        else if (code >= 51 && code <= 67) iconCode = "10d"; // rain
        else if (code >= 71 && code <= 77) iconCode = "13d"; // snow
        else if (code >= 80 && code <= 82) iconCode = "09d"; // showers
        else if (code >= 85 && code <= 86) iconCode = "13d"; // snow showers
        else if (code >= 95 && code <= 99) iconCode = "11d"; // thunderstorm
        
        return "https://openweathermap.org/img/wn/" + iconCode + "@4x.png";
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
