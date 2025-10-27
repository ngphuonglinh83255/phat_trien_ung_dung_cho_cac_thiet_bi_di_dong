package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditContactActivity extends AppCompatActivity {

    private EditText etName, etPhone;
    private DatabaseHelper db;
    private int contactId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        Button btnSave = findViewById(R.id.btnSave);

        db = new DatabaseHelper(this);

        if (getIntent().hasExtra("CONTACT_ID")) {
            contactId = getIntent().getIntExtra("CONTACT_ID", -1);
            Contact contact = db.getContact(contactId);
            if (contact != null) {
                etName.setText(contact.getName());
                etPhone.setText(contact.getPhone());
            }
        }

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (contactId == -1) {
                db.addContact(new Contact(name, phone));
                Toast.makeText(this, "Đã thêm danh bạ mới", Toast.LENGTH_SHORT).show();
            } else {
                db.updateContact(new Contact(contactId, name, phone));
                Toast.makeText(this, "Đã cập nhật danh bạ", Toast.LENGTH_SHORT).show();
            }

            finish();
        });
    }
}
