package com.example.cadastro_banco_de_dados.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cadastro_banco_de_dados.Activities.DAO.UserDAO;
import com.example.cadastro_banco_de_dados.Activities.Models.User;
import com.example.cadastro_banco_de_dados.R;

public class MainActivity extends AppCompatActivity {

    EditText editCadastroName;
    EditText editCadastroEmail;
    EditText editCadastroPassword;
    TextView textViewLogin;
    Button buttonCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCadastro = findViewById(R.id.buttonEditar);
        editCadastroName = findViewById(R.id.editEditarName);
        editCadastroEmail = findViewById(R.id.editEditarEmail);
        editCadastroPassword = findViewById(R.id.editEditarPassword);
        textViewLogin = findViewById(R.id.textViewLogin);

        SharedPreferences sp = getSharedPreferences("appCadastro",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editar = sp.edit();
        editar.putString("email", null);
        editar.commit();

        buttonCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                UserDAO userDao = new UserDAO(getApplicationContext(),
                        new User(editCadastroEmail.getText().toString(), editCadastroName.getText().toString(),
                                editCadastroPassword.getText().toString()));
                if(editCadastroEmail.getText().toString().isEmpty()){
                    editCadastroEmail.setError("Campo de email obrigatório");
                } else if (editCadastroName.getText().toString().isEmpty()) {
                    editCadastroName.setError("campo de nome obrigatório");
                }else if(editCadastroPassword.getText().toString().isEmpty()){
                    editCadastroPassword.setError("Campo de senha obrigatório");
                }else{
                    userDao.cadastroUser();

                    Intent it = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(it);
                }

            }
        });

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(it);
            }
        });
    }
}