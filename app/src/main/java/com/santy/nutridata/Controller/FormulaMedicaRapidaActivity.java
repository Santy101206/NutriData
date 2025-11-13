package com.santy.nutridata.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.santy.nutridata.Model.*;
import com.santy.nutridata.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FormulaMedicaRapidaActivity extends AppCompatActivity {

    private Spinner spinnerCategorias, spinnerEnfermedades;
    private EditText etNombrePaciente, etEdadPaciente;
    private Button btnGenerarFormula, btnImprimirFormula, btnVolver;
    private TextView tvFormula, tvRecomendaciones;
    private LinearLayout layoutFormula;
    private ManagerDB managerDB;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formula_medica_rapida);

        username = getIntent().getStringExtra("username");
        managerDB = new ManagerDB(this);
        initViews();
        cargarCategorias();
        setupListeners();
    }

    private void initViews() {
        spinnerCategorias = findViewById(R.id.spinnerCategorias);
        spinnerEnfermedades = findViewById(R.id.spinnerEnfermedades);
        etNombrePaciente = findViewById(R.id.etNombrePaciente);
        etEdadPaciente = findViewById(R.id.etEdadPaciente);
        btnGenerarFormula = findViewById(R.id.btnGenerarFormula);
        btnImprimirFormula = findViewById(R.id.btnImprimirFormula);
        btnVolver = findViewById(R.id.btnVolver);
        tvFormula = findViewById(R.id.tvFormula);
        tvRecomendaciones = findViewById(R.id.tvRecomendaciones);
        layoutFormula = findViewById(R.id.layoutFormula);
    }

    private void cargarCategorias() {
        List<String> categorias = managerDB.obtenerCategoriasEnfermedades();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategorias.setAdapter(adapter);

        spinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String categoriaSeleccionada = parent.getItemAtPosition(position).toString();
                cargarEnfermedades(categoriaSeleccionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void cargarEnfermedades(String categoria) {
        List<String> enfermedades = managerDB.obtenerEnfermedadesPorCategoria(categoria);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, enfermedades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEnfermedades.setAdapter(adapter);
    }

    private void setupListeners() {
        btnGenerarFormula.setOnClickListener(v -> generarFormulaMedica());
        btnImprimirFormula.setOnClickListener(v -> imprimirFormula());
        btnVolver.setOnClickListener(v -> finish());
    }

    private void generarFormulaMedica() {
        String nombrePaciente = etNombrePaciente.getText().toString().trim();
        String edadPaciente = etEdadPaciente.getText().toString().trim();
        String enfermedadSeleccionada = spinnerEnfermedades.getSelectedItem().toString();

        if (nombrePaciente.isEmpty()) {
            Toast.makeText(this, "Ingrese el nombre del paciente", Toast.LENGTH_SHORT).show();
            return;
        }

        if (enfermedadSeleccionada.equals("Seleccione enfermedad")) {
            Toast.makeText(this, "Por favor seleccione una enfermedad", Toast.LENGTH_SHORT).show();
            return;
        }

        FormulaMedica formula = managerDB.obtenerFormulaPorEnfermedad(enfermedadSeleccionada);

        if (formula != null) {
            String formulaCompleta = generarFormatoFormula(formula, nombrePaciente, edadPaciente);
            String recomendacionesCompletas = generarRecomendaciones(formula);

            tvFormula.setText(formulaCompleta);
            tvRecomendaciones.setText(recomendacionesCompletas);
            layoutFormula.setVisibility(View.VISIBLE);

            layoutFormula.post(() -> layoutFormula.requestFocus());
        } else {
            Toast.makeText(this, "No se encontró fórmula para la enfermedad seleccionada", Toast.LENGTH_SHORT).show();
        }
    }

    private String generarFormatoFormula(FormulaMedica formula, String nombrePaciente, String edad) {
        String infoPaciente = "👤 PACIENTE: " + nombrePaciente;
        if (!edad.isEmpty()) {
            infoPaciente += " | 🎂 EDAD: " + edad + " años";
        }

        return "🏥 FORMULA MÉDICA - " + formula.getNombreEnfermedad().toUpperCase() + "\n\n" +
                infoPaciente + "\n\n" +
                "💊 MEDICAMENTOS RECETADOS:\n" + formula.getMedicamentos() + "\n\n" +
                "📋 POSOLOGÍA:\n" + formula.getDosis() + "\n\n" +
                "⏱️ DURACIÓN DEL TRATAMIENTO:\n" + formula.getDuracion() + "\n\n" +
                "🚨 CONTRAINDICACIONES:\n" + formula.getContraindicaciones();
    }

    private String generarRecomendaciones(FormulaMedica formula) {
        return "📝 RECOMENDACIONES GENERALES:\n" + formula.getRecomendaciones() + "\n\n" +
                "🍽️ RECOMENDACIONES DIETÉTICAS:\n" + formula.getDieta() + "\n\n" +
                "💧 HIDRATACIÓN:\n" + formula.getHidratacion() + "\n\n" +
                "👨‍⚕️ MÉDICO TRATANTE:\nDr. " + username + "\n" +
                "📅 FECHA: " + new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
    }

    private void imprimirFormula() {
        Toast.makeText(this, "✅ Fórmula lista para imprimir o guardar", Toast.LENGTH_LONG).show();

        String formulaCompleta = tvFormula.getText().toString() + "\n\n" + tvRecomendaciones.getText().toString();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Fórmula Médica - Dr. " + username);
        shareIntent.putExtra(Intent.EXTRA_TEXT, formulaCompleta);
        startActivity(Intent.createChooser(shareIntent, "Compartir fórmula médica"));
    }
}