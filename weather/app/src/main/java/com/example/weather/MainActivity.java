package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edtCity;
    Button btnViewWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtCity = findViewById(R.id.edtCity);
        btnViewWeather = findViewById(R.id.btnViewWeather);

        btnViewWeather.setOnClickListener(v -> {
            String city = edtCity.getText().toString().trim();

            if (city.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên thành phố!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
            intent.putExtra("city", city);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_about) {
            new AlertDialog.Builder(this)
                    .setTitle("Giới thiệu ứng dụng")
                    .setMessage("Ứng dụng MyWeather - tra cứu thời tiết theo thời gian thực.")
                    .setPositiveButton("Đóng", null)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
