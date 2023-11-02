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
import android.widget.Toast;

import com.example.cadastro_banco_de_dados.Activities.DAO.UserDAO;
import com.example.cadastro_banco_de_dados.Activities.Models.User;
import com.example.cadastro_banco_de_dados.R;

public class EditUserActivity extends AppCompatActivity {

    EditText editEditarName;
    EditText editEditarEmail;
    EditText editEditarPassword;
    Button buttonEditar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        editEditarName = findViewById(R.id.editEditarName);
        editEditarEmail = findViewById(R.id.editEditarEmail);
        editEditarPassword = findViewById(R.id.editEditarPassword);
        buttonEditar = findViewById(R.id.buttonEditar);

        SharedPreferences sp = getSharedPreferences("appCadastro",
                Context.MODE_PRIVATE);

        String email = sp.getString("email", "abc");

        User user = new User();
        user.setEmail(email);
        UserDAO userDao = new UserDAO(getApplicationContext(), user);

        user = userDao.obterUsuarioPorEmail();

        editEditarName.setText(user.getName());
        editEditarEmail.setText(user.getEmail());
        editEditarPassword.setText(user.getPassword());

        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("appCadastro",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editar = sp.edit();
                editar.putString("email", editEditarEmail.getText().toString());
                editar.commit();

                UserDAO userDao = new UserDAO(getApplicationContext(),
                        new User(editEditarEmail.getText().toString(), editEditarName.getText().toString(),
                                editEditarPassword.getText().toString()));
                if(editEditarEmail.getText().toString().isEmpty()){
                    editEditarEmail.setError("Campo de email obrigatório");
                } else if (editEditarName.getText().toString().isEmpty()) {
                    editEditarName.setError("campo de nome obrigatório");
                }else if(editEditarPassword.getText().toString().isEmpty()){
                    editEditarPassword.setError("Campo de senha obrigatório");
                }else{
                    if(userDao.update()){
                        Intent it = new Intent(EditUserActivity.this, HomeActivity.class);
                        startActivity(it);
                    }else{
                        Toast.makeText(EditUserActivity.this, "Não foi possivel editar", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });


    }
}