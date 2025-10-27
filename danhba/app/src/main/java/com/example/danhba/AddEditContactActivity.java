package com.example.danhba;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditContactActivity extends AppCompatActivity {

    EditText nameInput, phoneInput;
    Button saveButton;
    DatabaseHelper db;
    int contactId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact);

        nameInput = findViewById(R.id.nameInput);
        phoneInput = findViewById(R.id.phoneInput);
        saveButton = findViewById(R.id.saveButton);
        db = new DatabaseHelper(this);

        // Nhận dữ liệu từ Intent (nếu sửa)
        if (getIntent().hasExtra("CONTACT_ID")) {
            contactId = getIntent().getIntExtra("CONTACT_ID", -1);
            nameInput.setText(getIntent().getStringExtra("CONTACT_NAME"));
            phoneInput.setText(getIntent().getStringExtra("CONTACT_PHONE"));
        }

        saveButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            String phone = phoneInput.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (contactId == -1) {
                db.addContact(new Contact(name, phone));
                Toast.makeText(this, "Đã thêm liên hệ", Toast.LENGTH_SHORT).show();
            } else {
                db.updateContact(new Contact(contactId, name, phone));
                Toast.makeText(this, "Đã cập nhật liên hệ", Toast.LENGTH_SHORT).show();
            }

            finish();
        });
    }
}
