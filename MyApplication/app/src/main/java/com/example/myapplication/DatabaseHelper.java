package com.example.myapplication;

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
    private static final String TABLE_NAME = "contacts";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_PHONE + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contact.getName());
        values.put(COLUMN_PHONE, contact.getPhone());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(cursor.getInt(0));
                contact.setName(cursor.getString(1));
                contact.setPhone(cursor.getString(2));
                contacts.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contacts;
    }

    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_PHONE},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Contact contact = new Contact(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            cursor.close();
            db.close();
            return contact;
        }
        return null;
    }

    public void updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contact.getName());
        values.put(COLUMN_PHONE, contact.getPhone());
        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(contact.getId())});
        db.close();
    }

    public void deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
