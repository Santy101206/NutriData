package com.santy.nutridata.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.santy.nutridata.R;

public class MenuActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private Button btnAddData, btnHistory, btnFormulasMedicas, btnLogout;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnAddData = findViewById(R.id.btnAddData);
        btnHistory = findViewById(R.id.btnHistory);
        btnFormulasMedicas = findViewById(R.id.btnFormulasMedicas);
        btnLogout = findViewById(R.id.btnLogout);

        username = getIntent().getStringExtra("username");
        tvWelcome.setText("Bienvenido, Dr. " + username);

        btnAddData.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, DataEntryActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, HistoryActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        btnFormulasMedicas.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, FormulasMedicasMenuActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, MainActivity.class));
            finish();
        });
    }
}