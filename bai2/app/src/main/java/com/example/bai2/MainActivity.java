package com.example.bai2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.*;
import android.view.*;
import android.widget.ViewSwitcher.ViewFactory;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private ImageSwitcher imageSwitcher;
    private GridView gridView;
    private FloatingActionButton fabAdd;
    private int[] imageIds = {
            R.drawable.img,
            R.drawable.img_1,
            R.drawable.img_2,
            R.drawable.img_3,
            R.drawable.img_4,
            R.drawable.img_5,
    };
    private String[] imageNames = {"Ảnh 1", "Ảnh 2", "Ảnh 3", "Ảnh 4", "Ảnh 5", "Ảnh 6"};

    private int currentIndex = 0;
    private Handler handler = new Handler();
    private Runnable slideRunnable;
    private SharedPreferences prefs;

    // Dùng ActivityResultLauncher để chọn ảnh từ thư viện
    private ActivityResultLauncher<String> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("GalleryPrefs", MODE_PRIVATE);
        currentIndex = prefs.getInt("last_image", 0);

        imageSwitcher = findViewById(R.id.imageSwitcher);
        gridView = findViewById(R.id.gridView);
        fabAdd = findViewById(R.id.fabAdd);

        imageSwitcher.setFactory(new ViewFactory() {
            @Override
            public View makeView() {
                ImageView img = new ImageView(getApplicationContext());
                img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                img.setLayoutParams(new ImageSwitcher.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
                return img;
            }
        });

        imageSwitcher.setImageResource(imageIds[currentIndex]);

        ImageAdapter adapter = new ImageAdapter(this, imageIds, imageNames);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            currentIndex = position;
            imageSwitcher.setImageResource(imageIds[position]);
            saveLastImage(position);
        });

        // Auto slide ảnh
        slideRunnable = new Runnable() {
            @Override
            public void run() {
                currentIndex = (currentIndex + 1) % imageIds.length;
                imageSwitcher.setImageResource(imageIds[currentIndex]);
                saveLastImage(currentIndex);
                handler.postDelayed(this, 3000); // đổi sau 3s
            }
        };
        handler.postDelayed(slideRunnable, 3000);

        // Chọn ảnh mới từ thư viện
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        imageSwitcher.setImageURI(uri);
                        Toast.makeText(this, "Ảnh mới được hiển thị!", Toast.LENGTH_SHORT).show();
                    }
                });

        fabAdd.setOnClickListener(v -> imagePickerLauncher.launch("image/*"));
    }

    private void saveLastImage(int index) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("last_image", index);
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(slideRunnable);
    }
}
