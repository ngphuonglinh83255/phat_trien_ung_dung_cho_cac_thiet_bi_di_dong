package com.example.danhba;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CONTACTS = "contacts";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_CONTACTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PHONE + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    public void addContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contact.getName());
        values.put(COLUMN_PHONE, contact.getPhone());
        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public void updateContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contact.getName());
        values.put(COLUMN_PHONE, contact.getPhone());
        db.update(TABLE_CONTACTS, values, COLUMN_ID + "=?", new String[]{String.valueOf(contact.getId())});
        db.close();
    }

    public List<Contact> getAllContacts() {
        List<Contact> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACTS, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(new Contact(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }
}
