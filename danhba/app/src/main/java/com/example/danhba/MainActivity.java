package com.example.danhba;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Button;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button addButton;
    DatabaseHelper db;
    ContactAdapter adapter;
    List<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.contactList);
        addButton = findViewById(R.id.addButton);

        db = new DatabaseHelper(this);

        // Thêm dữ liệu mẫu (nếu database trống)
        if (db.getAllContacts().isEmpty()) {
            db.addContact(new Contact("Nguyễn Văn A", "0123456789"));
            db.addContact(new Contact("Trần Thị B", "0987654321"));
        }

        loadContacts();

        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditContactActivity.class);
            startActivity(intent);
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Contact c = contactList.get(position);
            Intent intent = new Intent(MainActivity.this, AddEditContactActivity.class);
            intent.putExtra("CONTACT_ID", c.getId());
            intent.putExtra("CONTACT_NAME", c.getName());
            intent.putExtra("CONTACT_PHONE", c.getPhone());
            startActivity(intent);
        });
    }

    private void loadContacts() {
        contactList = db.getAllContacts();
        adapter = new ContactAdapter(this, contactList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadContacts();
    }
}
