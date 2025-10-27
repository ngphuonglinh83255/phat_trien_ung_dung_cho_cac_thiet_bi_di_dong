package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.os.Handler;
import android.os.Looper;

import org.json.JSONObject;
import java.io.IOException;
import okhttp3.*;

public class WeatherActivity extends AppCompatActivity {

    TextView tvCity, tvTemp, tvHumidity, tvStatus;
    ImageView imgWeather;

    final String API_KEY = "beaa2aae9d1c4fde82b82fa841e9e4cf";
    final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric&lang=vi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        tvCity = findViewById(R.id.tvCity);
        tvTemp = findViewById(R.id.tvTemp);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvStatus = findViewById(R.id.tvStatus);
        imgWeather = findViewById(R.id.imgWeather);

        String city = getIntent().getStringExtra("city");
        tvCity.setText("Thành phố: " + city);

        getWeatherData(city);
    }

    private void getWeatherData(String cityName) {
        OkHttpClient client = new OkHttpClient();
        String url = String.format(BASE_URL, cityName, API_KEY);

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                new Handler(Looper.getMainLooper()).post(() ->
                        Toast.makeText(WeatherActivity.this, "Không thể kết nối API", Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    new Handler(Looper.getMainLooper()).post(() ->
                            Toast.makeText(WeatherActivity.this, "Lỗi dữ liệu hoặc API key chưa kích hoạt", Toast.LENGTH_SHORT).show()
                    );
                    return;
                }

                try {
                    String jsonData = response.body().string();
                    JSONObject jsonObject = new JSONObject(jsonData);

                    JSONObject main = jsonObject.getJSONObject("main");
                    double temp = main.getDouble("temp");
                    int humidity = main.getInt("humidity");

                    JSONObject weather = jsonObject.getJSONArray("weather").getJSONObject(0);
                    String status = weather.getString("description");
                    String icon = weather.getString("icon");

                    new Handler(Looper.getMainLooper()).post(() -> {
                        tvTemp.setText("Nhiệt độ: " + temp + "°C");
                        tvHumidity.setText("Độ ẩm: " + humidity + "%");
                        tvStatus.setText("Trạng thái: " + status);

                        if (icon.contains("01")) {
                            imgWeather.setImageResource(R.drawable.sun);
                        } else if (icon.contains("09") || icon.contains("10")) {
                            imgWeather.setImageResource(R.drawable.rain);
                        } else {
                            imgWeather.setImageResource(R.drawable.cloud);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    new Handler(Looper.getMainLooper()).post(() ->
                            Toast.makeText(WeatherActivity.this, "Lỗi xử lý dữ liệu JSON", Toast.LENGTH_SHORT).show()
                    );
                }
            }
        });
    }
}
