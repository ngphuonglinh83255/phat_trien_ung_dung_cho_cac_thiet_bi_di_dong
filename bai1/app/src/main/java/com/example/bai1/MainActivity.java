package com.example.bai1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    EditText edtA, edtB;
    TextView tvResult, tvHistory;
    Button btnAdd, btnSub, btnMul, btnDiv, btnClearHistory;
    StringBuilder history = new StringBuilder(); // Lưu lịch sử tính toán

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ
        edtA = findViewById(R.id.edtA);
        edtB = findViewById(R.id.edtB);
        tvResult = findViewById(R.id.tvResult);
        tvHistory = findViewById(R.id.tvHistory);
        btnAdd = findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSub);
        btnMul = findViewById(R.id.btnMul);
        btnDiv = findViewById(R.id.btnDiv);
        btnClearHistory = findViewById(R.id.btnClearHistory);

        View.OnClickListener listener = v -> {
            String strA = edtA.getText().toString().trim();
            String strB = edtB.getText().toString().trim();

            if (strA.isEmpty() || strB.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ hai số!", Toast.LENGTH_SHORT).show();
                return;
            }

            double a = Double.parseDouble(strA);
            double b = Double.parseDouble(strB);
            double result = 0;
            String expression = "";

            if (v.getId() == R.id.btnAdd) {
                result = a + b;
                expression = a + " + " + b + " = " + result;
            } else if (v.getId() == R.id.btnSub) {
                result = a - b;
                expression = a + " - " + b + " = " + result;
            } else if (v.getId() == R.id.btnMul) {
                result = a * b;
                expression = a + " × " + b + " = " + result;
            } else if (v.getId() == R.id.btnDiv) {
                if (b == 0) {
                    Toast.makeText(this, "Không thể chia cho 0!", Toast.LENGTH_SHORT).show();
                    return;
                }
                result = a / b;
                expression = a + " ÷ " + b + " = " + result;
            }

            tvResult.setText("Kết quả: " + result);

            // Lưu lịch sử
            history.append(expression).append("\n");
            tvHistory.setText(history.toString());
        };

        btnAdd.setOnClickListener(listener);
        btnSub.setOnClickListener(listener);
        btnMul.setOnClickListener(listener);
        btnDiv.setOnClickListener(listener);

        btnClearHistory.setOnClickListener(v -> {
            history.setLength(0);
            tvHistory.setText("(Chưa có phép tính nào)");
            Toast.makeText(this, "Đã xóa lịch sử!", Toast.LENGTH_SHORT).show();
        });
    }
}
