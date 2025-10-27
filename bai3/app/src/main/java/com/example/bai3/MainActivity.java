package com.example.bai3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    EditText edtCity;
    Button btnViewWeather;
    ListView lvHistory;
    ArrayAdapter<String> adapter;
    ArrayList<String> historyList;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtCity = findViewById(R.id.edtCity);
        btnViewWeather = findViewById(R.id.btnViewWeather);
        lvHistory = findViewById(R.id.lvHistory);

        prefs = getSharedPreferences("weather_prefs", MODE_PRIVATE);
        Set<String> set = prefs.getStringSet("history", new HashSet<>());
        historyList = new ArrayList<>(set);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyList);
        lvHistory.setAdapter(adapter);

        btnViewWeather.setOnClickListener(v -> {
            String city = edtCity.getText().toString().trim();
            if(city.isEmpty()){
                Toast.makeText(this, "Vui lòng nhập tên thành phố", Toast.LENGTH_SHORT).show();
                return;
            }

            // lưu vào lịch sử
            if(!historyList.contains(city)){
                historyList.add(0, city);
                adapter.notifyDataSetChanged();
                Set<String> newSet = new HashSet<>(historyList);
                prefs.edit().putStringSet("history", newSet).apply();
            }

            Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
            intent.putExtra("city_name", city);
            startActivity(intent);
        });

        lvHistory.setOnItemClickListener((parent, view, position, id) -> {
            String city = historyList.get(position);
            edtCity.setText(city);
        });
    }
}
