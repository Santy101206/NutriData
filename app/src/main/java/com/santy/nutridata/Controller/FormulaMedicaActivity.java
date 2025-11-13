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

public class FormulaMedicaActivity extends AppCompatActivity {

    private Spinner spinnerCategorias, spinnerEnfermedades;
    private Button btnGenerarFormula, btnGuardarFormula, btnVolver;
    private TextView tvFormula, tvRecomendaciones, tvInfoPaciente;
    private LinearLayout layoutFormula;
    private ManagerDB managerDB;
    private String username;
    private String nombrePaciente;
    private int edad;
    private double peso;
    private String alergias;
    private int idPaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formula_medica);

        idPaciente = getIntent().getIntExtra("idPaciente", -1);
        nombrePaciente = getIntent().getStringExtra("nombrePaciente");
        edad = getIntent().getIntExtra("edad", 0);
        peso = getIntent().getDoubleExtra("peso", 0);
        alergias = getIntent().getStringExtra("alergias");
        username = getIntent().getStringExtra("username");

        managerDB = new ManagerDB(this);
        initViews();
        cargarCategorias();
        setupListeners();
        mostrarInfoPaciente();
    }

    private void initViews() {
        spinnerCategorias = findViewById(R.id.spinnerCategorias);
        spinnerEnfermedades = findViewById(R.id.spinnerEnfermedades);
        btnGenerarFormula = findViewById(R.id.btnGenerarFormula);
        btnGuardarFormula = findViewById(R.id.btnGuardarFormula);
        btnVolver = findViewById(R.id.btnVolver);
        tvFormula = findViewById(R.id.tvFormula);
        tvRecomendaciones = findViewById(R.id.tvRecomendaciones);
        tvInfoPaciente = findViewById(R.id.tvInfoPaciente);
        layoutFormula = findViewById(R.id.layoutFormula);
    }

    private void mostrarInfoPaciente() {
        String info = "👤 Paciente: " + nombrePaciente +
                " | 🎂 Edad: " + edad + " años" +
                " | ⚖️ Peso: " + peso + " kg";
        if (alergias != null && !alergias.isEmpty()) {
            info += " | ⚠️ Alergias: " + alergias;
        }
        tvInfoPaciente.setText(info);
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
        btnGuardarFormula.setOnClickListener(v -> guardarFormulaEnHistorial());
        btnVolver.setOnClickListener(v -> finish());
    }

    private void generarFormulaMedica() {
        String enfermedadSeleccionada = spinnerEnfermedades.getSelectedItem().toString();

        if (enfermedadSeleccionada.equals("Seleccione enfermedad")) {
            Toast.makeText(this, "Por favor seleccione una enfermedad", Toast.LENGTH_SHORT).show();
            return;
        }

        FormulaMedica formula = managerDB.obtenerFormulaPorEnfermedad(enfermedadSeleccionada);

        if (formula != null) {
            if (verificarAlergias(formula.getMedicamentos())) {
                Toast.makeText(this, "⚠️ ALERTA: Medicamento contraindicado por alergias del paciente", Toast.LENGTH_LONG).show();
            }

            String formulaCompleta = generarFormatoFormula(formula);
            String recomendacionesCompletas = generarRecomendaciones(formula);

            tvFormula.setText(formulaCompleta);
            tvRecomendaciones.setText(recomendacionesCompletas);
            layoutFormula.setVisibility(View.VISIBLE);

            layoutFormula.post(() -> layoutFormula.requestFocus());
        } else {
            Toast.makeText(this, "No se encontró fórmula para la enfermedad seleccionada", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean verificarAlergias(String medicamentos) {
        if (alergias == null || alergias.isEmpty()) return false;

        String[] alergiasArray = alergias.split(",");
        for (String alergia : alergiasArray) {
            String alergiaLimpia = alergia.trim().toLowerCase();
            if (!alergiaLimpia.isEmpty() && medicamentos.toLowerCase().contains(alergiaLimpia)) {
                return true;
            }
        }
        return false;
    }

    private String generarFormatoFormula(FormulaMedica formula) {
        return "🏥 FORMULA MÉDICA - " + formula.getNombreEnfermedad().toUpperCase() + "\n\n" +
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

    private void guardarFormulaEnHistorial() {
        if (idPaciente == -1) {
            Toast.makeText(this, "Error: No se pudo identificar al paciente", Toast.LENGTH_SHORT).show();
            return;
        }

        String enfermedad = spinnerEnfermedades.getSelectedItem().toString();
        String tratamientoCompleto = tvFormula.getText().toString() + "\n\n" + tvRecomendaciones.getText().toString();

        boolean exito = managerDB.actualizarTratamiento(idPaciente, tratamientoCompleto);

        if (exito) {
            Toast.makeText(this, "✅ Tratamiento guardado en historial de " + nombrePaciente, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, HistoryActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "❌ Error al guardar el tratamiento", Toast.LENGTH_SHORT).show();
        }
    }
}