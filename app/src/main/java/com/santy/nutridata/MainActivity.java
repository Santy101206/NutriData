package com.santy.nutridata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etUser, etPass;
    Button btnLogin, btnRegister;
    DbHelper db;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DbHelper(this);

        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        // Inicializa el sonido (coloca app/src/main/res/raw/login_success.mp3)
        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.login_success);
        } catch (Exception e) {
            mediaPlayer = null;
        }

        btnRegister.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RegisterActivity.class)));

        btnLogin.setOnClickListener(v -> {
            String user = etUser.getText().toString().trim();
            String pass = etPass.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Rellena usuario y contraseña", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean ok = db.checkUser(user, pass);
            if (ok) {
                // reproducir sonido
                if (mediaPlayer != null) mediaPlayer.start();

                Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, MenuActivity.class);
                i.putExtra("username", user);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
