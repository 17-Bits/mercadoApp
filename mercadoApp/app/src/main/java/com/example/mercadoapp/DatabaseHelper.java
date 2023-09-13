package com.example.mercadoapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "Signup.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "Signup.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("create Table allusers(email TEXT primary key, password TEXT, name TEXT)");
        MyDatabase.execSQL("create Table allproducts(nameProduct TEXT, unit TEXT, quantity DOUBLE, id INTEGER PRIMARY KEY AUTOINCREMENT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int i, int i1) {
        MyDatabase.execSQL("drop Table if exists allusers");
        MyDatabase.execSQL("drop Table if exists allproducts");

    }

    public Boolean insertData(String name, String email, String password){
        SQLiteDatabase MyDataBase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDataBase.insert("allusers", null, contentValues);

        if (result == -1){
            return false;
        }else {
            return true;
        }
    }
    public Boolean insertProduct(String nameProduct, String unit, Double quantity) {
        SQLiteDatabase MyDataBase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nameProduct", nameProduct);
        contentValues.put("unit", unit);
        contentValues.put("quantity", quantity);


        long result = MyDataBase.insert("allproducts", null, contentValues);
        return result != -1;
    }


    public Boolean checkEmail(String email){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from allusers where email = ?", new String[]{email});

        if (cursor.getCount()>0){
            return true;
        } else {
            return false;
            }
        }

        public  Boolean checkEmailPassword(String email, String password){
            SQLiteDatabase MyDatabase = this.getWritableDatabase();
            Cursor cursor = MyDatabase.rawQuery("Select * from allusers where email = ? and password = ?", new String[]{email , password});

            if (cursor.getCount()>0){
                return true;
            } else {
                return false;
            }


        }
    public String getUserNameByEmail(String email) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        String[] projection = {"name"};
        String selection = "email = ?";
        String[] selectionArgs = {email};
        Cursor cursor = MyDatabase.query("allusers", projection, selection, selectionArgs, null, null, null);

        String userName = null;
        if (cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndexOrThrow("name");
            userName = cursor.getString(nameIndex);
        }

        cursor.close();
        return userName;
    }

    public ArrayList<String> getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> productList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM allproducts", null);

        if (cursor.moveToFirst()) {
            int productNameIndex = cursor.getColumnIndex("nameProduct");
            int productUnitIndex = cursor.getColumnIndex("unit");
            int productQuantityIndex = cursor.getColumnIndex("quantity");

            do {
                String productName = cursor.getString(productNameIndex);
                String productUnit = cursor.getString(productUnitIndex);
                double productQuantity = cursor.getDouble(productQuantityIndex);

                String productDetails = "Product: " + productName + ", Unit: " + productUnit + ", Quantity: " + productQuantity;
                productList.add(productDetails);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return productList;
    }


}
