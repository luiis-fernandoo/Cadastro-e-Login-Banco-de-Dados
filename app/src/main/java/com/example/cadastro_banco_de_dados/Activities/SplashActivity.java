package com.example.cadastro_banco_de_dados.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.example.cadastro_banco_de_dados.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "HomeLog";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.d(TAG, "onCreate: Activity criada"); // Adicione este log
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SharedPreferences sp = getSharedPreferences("appCadastro",
                        Context.MODE_PRIVATE);
                String email = sp.getString("email", "abc");

                if(email.equals("abc")){
                    Intent it = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(it);
                }else{
                    Intent it = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(it);
                }


            }
        }, 2000);
    }
}