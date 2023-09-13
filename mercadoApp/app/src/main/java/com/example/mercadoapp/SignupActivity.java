package com.example.mercadoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mercadoapp.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.signupName.getText().toString();
                String email = binding.signupEmail.getText().toString();
                String password = binding.signupPassword.getText().toString();
                String confirmPassword = binding.signupConfirm.getText().toString();

                if (name.equals("") || email.equals("") || password.equals("") || confirmPassword.equals(""))
                    Toast.makeText(SignupActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                else {
                    if (password.equals(confirmPassword)){
                        Boolean checkUserEmail = databaseHelper.checkEmail(email);

                        if (!checkUserEmail){
                            Boolean insert = databaseHelper.insertData(name, email, password);

                            if (insert){
                                Toast.makeText(SignupActivity.this, "Signup Successfully", Toast.LENGTH_SHORT).show();
                                // Verificar se o email já está autenticado
                                Boolean isAuthenticated = databaseHelper.checkEmail(email);
                                if (isAuthenticated) {
                                    // Se o email estiver autenticado, redirecionar para MainActivity
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("loggedInUserEmail", email);
                                    startActivity(intent);
                                } else {
                                    // Se o email não estiver autenticado, redirecionar para LoginActivity
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                }
                            } else {
                                Toast.makeText(SignupActivity.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(SignupActivity.this, "User already exist, Please Login", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignupActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}