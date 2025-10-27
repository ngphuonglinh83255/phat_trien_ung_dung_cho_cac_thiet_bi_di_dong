package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Contact> contactList;

    public ContactAdapter(Context context, ArrayList<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Object getItem(int i) {
        return contactList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);

        TextView name = view.findViewById(R.id.contactName);
        TextView phone = view.findViewById(R.id.contactPhone);

        Contact contact = contactList.get(i);
        name.setText(contact.getName());
        phone.setText(contact.getPhone());

        return view;
    }
}
