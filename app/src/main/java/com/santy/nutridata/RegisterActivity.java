package com.santy.nutridata;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText etUser, etPass;
    Button btnRegister;
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DbHelper(this);

        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            String username = etUser.getText().toString().trim();
            String pass = etPass.getText().toString().trim();

            if (username.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = db.insertUser(username, pass);
            if (inserted) {
                Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                finish(); // vuelve al login
            } else {
                Toast.makeText(this, "Usuario ya existe o error al registrar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
