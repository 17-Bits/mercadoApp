package com.example.mercadoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mercadoapp.databinding.ActivityListProductBinding;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListProductActivity extends AppCompatActivity {

    ActivityListProductBinding binding;
    DatabaseHelper databaseHelper;
    ListView listViewProducts;
    ArrayList<String> productList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        listViewProducts = binding.listViewProducts;
        productList = databaseHelper.getAllProducts();

        adapter = new ArrayAdapter(this, R.layout.list_item_product, R.id.textViewProductName, productList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView textViewProductName = view.findViewById(R.id.textViewProductName);
                TextView textViewProductDetails = view.findViewById(R.id.textViewProductDetails);

                String productDetails = productList.get(position);
                String[] detailsArray = productDetails.split(", ");

                if (detailsArray.length >= 3) {
                    String productName = detailsArray[0].replace("Product: ", "");
                    String productUnit = detailsArray[1].replace("Unit: ", "");
                    String productQuantity = detailsArray[2].replace("Quantity: ", "");

                    textViewProductName.setText(productName);
                    textViewProductDetails.setText("Unit: " + productUnit + ", Quantity: " + productQuantity);
                }

                return view;
            }
        };

        listViewProducts.setAdapter(adapter);
    }
}
