package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText nameInput, phoneInput;
    FloatingActionButton addButton;
    ListView listView;
    ArrayList<Contact> contactList;
    ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.nameInput);
        phoneInput = findViewById(R.id.phoneInput);
        addButton = findViewById(R.id.addButton);
        listView = findViewById(R.id.contactList);

        contactList = new ArrayList<>();
        adapter = new ContactAdapter(this, contactList);
        listView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameInput.getText().toString().trim();
                String phone = phoneInput.getText().toString().trim();
                if (name.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ!", Toast.LENGTH_SHORT).show();
                } else {
                    contactList.add(new Contact(name, phone));
                    adapter.notifyDataSetChanged();
                    nameInput.setText("");
                    phoneInput.setText("");
                }
            }
        });
    }
}
