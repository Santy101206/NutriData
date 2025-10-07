package com.santy.nutridata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    TextView tvWelcome;
    Button btnIngresarDatos, btnVerHistorial, btnLogout;
    String username;
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        db = new DbHelper(this);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnIngresarDatos = findViewById(R.id.btnIngresarDatos);
        btnVerHistorial = findViewById(R.id.btnVerHistorial);
        btnLogout = findViewById(R.id.btnLogout);

        username = getIntent().getStringExtra("username");
        if (username == null) username = "Usuario";
        tvWelcome.setText("Bienvenido, " + username + "!");

        btnIngresarDatos.setOnClickListener(v -> {
            Intent i = new Intent(MenuActivity.this, DataEntryActivity.class);
            i.putExtra("username", username);
            startActivity(i);
        });

        btnVerHistorial.setOnClickListener(v -> {
            Intent i = new Intent(MenuActivity.this, HistoryActivity.class);
            i.putExtra("username", username);
            startActivity(i);
        });

        btnLogout.setOnClickListener(v -> {
            Intent i = new Intent(MenuActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        });
    }
}

