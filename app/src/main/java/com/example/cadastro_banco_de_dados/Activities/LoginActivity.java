package com.example.cadastro_banco_de_dados.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cadastro_banco_de_dados.Activities.DAO.UserDAO;
import com.example.cadastro_banco_de_dados.Activities.Models.User;
import com.example.cadastro_banco_de_dados.R;

public class LoginActivity extends AppCompatActivity {

    EditText editLoginEmail;
    EditText editLoginPassword;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editLoginEmail = findViewById(R.id.editLoginEmail);
        editLoginPassword = findViewById(R.id.editLoginPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sp = getSharedPreferences("appCadastro",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor login = sp.edit();
                login.putString("email", editLoginEmail.getText().toString());
                login.commit();



                UserDAO userDao = new UserDAO(getApplicationContext(),
                        new User(editLoginEmail.getText().toString(),
                                editLoginPassword.getText().toString()));

                if(userDao.loginUser()){
                    Intent it = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(it);
                }else{
                    Toast.makeText(LoginActivity.this, "Dados Errados", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}