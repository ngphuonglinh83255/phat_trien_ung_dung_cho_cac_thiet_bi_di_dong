package com.example.bai3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;

import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {

    TextView tvTemp, tvHumidity, tvCondition;
    ImageView imgWeather;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        tvTemp = findViewById(R.id.tvTemp);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvCondition = findViewById(R.id.tvCondition);
        imgWeather = findViewById(R.id.imgWeather);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        String city = getIntent().getStringExtra("city_name");
        // TODO: Lấy tọa độ thực tế của city, tạm dùng lat/lon cố định
        double lat = 21.0278;  // Hà Nội
        double lon = 105.8342;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApi api = retrofit.create(WeatherApi.class);
        api.getWeather(lat, lon, "minutely,hourly,daily,alerts", "YOUR_API_KEY", "metric")
                .enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        if(response.isSuccessful() && response.body() != null){
                            double temp = response.body().current.temp;
                            int humidity = response.body().current.humidity;
                            String condition = response.body().current.weather.get(0).main;

                            tvTemp.setText(temp + "°C");
                            tvHumidity.setText(humidity + "%");
                            tvCondition.setText(condition);

                            // icon mặc định, bạn có thể dùng Glide/Picasso để load từ API
                            imgWeather.setImageResource(R.drawable.ic_sun);
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        Toast.makeText(WeatherActivity.this, "Không thể lấy dữ liệu thời tiết", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
