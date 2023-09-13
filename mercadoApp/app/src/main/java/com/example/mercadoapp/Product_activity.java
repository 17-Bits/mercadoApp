package com.example.mercadoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Toast;

import com.example.mercadoapp.databinding.ActivityProductBinding;


public class Product_activity extends AppCompatActivity {

    ActivityProductBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.productBtnRegisterproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productName = binding.productName.getText().toString().trim();
                String productUnit = binding.productUnit.getText().toString().trim();
                String productQuantityString = binding.productQuantity.getText().toString().trim();

                if (productName.isEmpty() || productUnit.isEmpty() || productQuantityString.isEmpty()) {
                    Toast.makeText(Product_activity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {

                    // Convert the quantity to a decimal value
                    double productQuantity = Double.parseDouble(productQuantityString);
                    // Perform the database insertion here

                    // Assuming you have a method in your DatabaseHelper class to insert the product
                    boolean insertSuccess = databaseHelper.insertProduct(productName, productUnit, productQuantity);

                    if (insertSuccess) {
                        Toast.makeText(Product_activity.this, "Product registered successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Product_activity.this, "Failed to register product", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        binding.productBtnlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Product_activity.this, ListProductActivity.class);
                startActivity(intent);
            }
        });
    }
}