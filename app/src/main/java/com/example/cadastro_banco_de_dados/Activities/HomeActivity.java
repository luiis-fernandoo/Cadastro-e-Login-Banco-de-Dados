package com.example.cadastro_banco_de_dados.Activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cadastro_banco_de_dados.Activities.DAO.UserDAO;
import com.example.cadastro_banco_de_dados.Activities.Models.User;
import com.example.cadastro_banco_de_dados.R;

public class HomeActivity extends AppCompatActivity {

    TextView textViewSharedName;
    TextView textViewSharedEmail;


    Button buttonEditarUser;
    Button buttonDeletarUser;
    Button Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        textViewSharedName = findViewById(R.id.textViewSharedName);
        textViewSharedEmail = findViewById(R.id.textViewSharedEmail);
        buttonDeletarUser = findViewById(R.id.buttonDeletarUser);
        buttonEditarUser = findViewById(R.id.buttonEditarUser);
        Logout = findViewById(R.id.Logout);

        SharedPreferences sp = getSharedPreferences("appCadastro",
                Context.MODE_PRIVATE);

        String email = sp.getString("email", "abc");
        if(email.isEmpty()){
            Intent it = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(it);
        }else{
            User user = new User();
            user.setEmail(email);
            UserDAO userDao = new UserDAO(getApplicationContext(), user);

            user = userDao.obterUsuarioPorEmail();

            textViewSharedName.setText(user.getName());
            textViewSharedEmail.setText(user.getEmail());

            buttonEditarUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent(HomeActivity.this, EditUserActivity.class);
                    startActivity(it);
                }
            });

            buttonDeletarUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(userDao.delete()){

                        Toast.makeText(HomeActivity.this, "Dados excluídos", Toast.LENGTH_LONG).show();
                        Intent it = new Intent(HomeActivity.this, MainActivity.class);
                        startActivity(it);
                    }else{
                        Toast.makeText(HomeActivity.this, "Não foi possivel excluir", Toast.LENGTH_LONG).show();

                    }

                }
            });

            Logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sp = getSharedPreferences("appCadastro",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editar = sp.edit();
                    editar.putString("email", null);
                    editar.commit();
                    Intent it = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(it);
                }
            });

        }

    }
}