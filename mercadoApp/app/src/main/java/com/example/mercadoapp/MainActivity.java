package com.example.mercadoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mercadoapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        // Obtém o email do campo de entrada da atividade de login
        String loggedInUserEmail = getIntent().getStringExtra("loggedInUserEmail");
        String loggedInUserName = getLoggedInUserName(loggedInUserEmail);

        if (loggedInUserName != null && !loggedInUserName.isEmpty()) {
            TextView menuNameTextView = binding.menuName;
            menuNameTextView.setText(loggedInUserName);
        } else {
            TextView menuNameTextView = binding.menuName;
            menuNameTextView.setText("Nome de Usuário");
        }

        binding.menuBtnregisterProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Product_activity.class);
                startActivity(intent);
            }
        });
        binding.menuBtnlistProduc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListProductActivity.class);
                startActivity(intent);
            }
        });
    }

    // Validação nome
    private String getLoggedInUserName(String loggedInUserEmail) {
        String userName = databaseHelper.getUserNameByEmail(loggedInUserEmail);
        return userName != null ? userName : "";
    }

    // Retorna para a tela de login ao pressionar o botão "Voltar"
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
