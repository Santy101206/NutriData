package com.santy.nutridata.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.santy.nutridata.R;

public class FormulasMedicasMenuActivity extends AppCompatActivity {

    private TextView tvTitle;
    private Button btnBuscarPaciente, btnFormulaRapida, btnVolver;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulas_medicas_menu);

        tvTitle = findViewById(R.id.tvTitle);
        btnBuscarPaciente = findViewById(R.id.btnBuscarPaciente);
        btnFormulaRapida = findViewById(R.id.btnFormulaRapida);
        btnVolver = findViewById(R.id.btnVolver);

        username = getIntent().getStringExtra("username");
        tvTitle.setText("Asistente de Fórmulas Médicas\nDr. " + username);

        btnBuscarPaciente.setOnClickListener(v -> {
            Intent intent = new Intent(FormulasMedicasMenuActivity.this, HistoryActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        btnFormulaRapida.setOnClickListener(v -> {
            Intent intent = new Intent(FormulasMedicasMenuActivity.this, FormulaMedicaRapidaActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        btnVolver.setOnClickListener(v -> finish());
    }
}