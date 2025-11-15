package com.santy.nutridata.Controller;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.santy.nutridata.Model.*;
import com.santy.nutridata.R;

public class DataEntryActivity extends AppCompatActivity {

    private EditText etNombrePaciente, etTipoSangre, etDireccion, etPeso, etAltura, etEdad;
    private EditText etEnfermedades, etAlergias, etSintomas;
    private EditText etRespNombre, etRespCedula, etRespEdad, etRespTelefono, etRespRelacion;
    private LinearLayout layoutResponsable;
    private Button btnGuardar, btnVolver;
    private String username;
    private ManagerDB managerDB;
    private TextView tvResultadoIMC, tvClasificacion;


    private TextWatcher imcTextWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);

        initViews();
        managerDB = new ManagerDB(this);
        username = getIntent().getStringExtra("username");

        layoutResponsable.setVisibility(View.GONE);

        imcTextWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                calcularIMC();
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        };

        etPeso.addTextChangedListener(imcTextWatcher);
        etAltura.addTextChangedListener(imcTextWatcher);
        etEdad.addTextChangedListener(imcTextWatcher);

        etEdad.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                checkEdad();
                calcularIMC();
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        btnGuardar.setOnClickListener(v -> guardarDatos());
        btnVolver.setOnClickListener(v -> finish());
    }

    private void initViews() {
        etNombrePaciente = findViewById(R.id.etNombrePaciente);
        etTipoSangre = findViewById(R.id.etTipoSangre);
        etDireccion = findViewById(R.id.etDireccion);
        etPeso = findViewById(R.id.etPeso);
        etAltura = findViewById(R.id.etAltura);
        etEdad = findViewById(R.id.etEdad);
        etEnfermedades = findViewById(R.id.etEnfermedades);
        etAlergias = findViewById(R.id.etAlergias);
        etSintomas = findViewById(R.id.etSintomas);
        layoutResponsable = findViewById(R.id.layoutResponsable);
        etRespNombre = findViewById(R.id.etRespNombre);
        etRespCedula = findViewById(R.id.etRespCedula);
        etRespEdad = findViewById(R.id.etRespEdad);
        etRespTelefono = findViewById(R.id.etRespTelefono);
        etRespRelacion = findViewById(R.id.etRespRelacion);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVolver = findViewById(R.id.btnVolver);
        tvResultadoIMC = findViewById(R.id.tvResultadoIMC);
        tvClasificacion = findViewById(R.id.tvClasificacion);
    }

    private void guardarDatos() {
        try {
            if (!validarCamposBasicos()) return;

            double peso = Double.parseDouble(etPeso.getText().toString().trim());
            double altura = Double.parseDouble(etAltura.getText().toString().trim());
            int edad = Integer.parseInt(etEdad.getText().toString().trim());
            double imc = peso / (altura * altura);
            String clasificacion = clasificarIMC(imc, edad);
            String prioridad = determinarPrioridad(clasificacion);

            Datos datos = new Datos();
            datos.setUsuario(username);
            datos.setNombrePaciente(etNombrePaciente.getText().toString().trim());
            datos.setTipoSangre(etTipoSangre.getText().toString().trim());
            datos.setDireccion(etDireccion.getText().toString().trim());
            datos.setPeso(peso);
            datos.setAltura(altura);
            datos.setEdad(edad);
            datos.setEnfermedades(etEnfermedades.getText().toString().trim());
            datos.setAlergias(etAlergias.getText().toString().trim());
            datos.setSintomas(etSintomas.getText().toString().trim());
            datos.setImc(imc);
            datos.setClasificacion(clasificacion);
            datos.setPrioridad(prioridad);
            datos.setMedicoResponsable("Dr. " + username);
            datos.setTratamiento("");

            if (edad < 18) {
                if (!validarCamposResponsable()) return;
                datos.setRespNombre(etRespNombre.getText().toString().trim());
                datos.setRespCedula(etRespCedula.getText().toString().trim());
                datos.setRespEdad(Integer.parseInt(etRespEdad.getText().toString().trim()));
                datos.setRespTelefono(etRespTelefono.getText().toString().trim());
                datos.setRespRelacion(etRespRelacion.getText().toString().trim());
            }

            boolean exito = managerDB.insertarDatos(datos);
            if (exito) {
                Toast.makeText(this, "✅ Datos del paciente guardados\n" + clasificacion, Toast.LENGTH_LONG).show();
                limpiarCampos();
            } else {
                Toast.makeText(this, "❌ Error al guardar los datos", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "⚠️ Revise los datos ingresados", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkEdad() {
        try {
            int edad = Integer.parseInt(etEdad.getText().toString());
            layoutResponsable.setVisibility(edad < 18 ? View.VISIBLE : View.GONE);
        } catch (NumberFormatException e) {
            layoutResponsable.setVisibility(View.GONE);
        }
    }

    private void calcularIMC() {
        try {
            String pesoStr = etPeso.getText().toString().trim();
            String alturaStr = etAltura.getText().toString().trim();
            String edadStr = etEdad.getText().toString().trim();

            if (pesoStr.isEmpty() || alturaStr.isEmpty() || edadStr.isEmpty()) {
                tvResultadoIMC.setText("IMC: --");
                tvClasificacion.setText("Complete los datos para calcular");
                return;
            }

            double peso = Double.parseDouble(pesoStr);
            double altura = Double.parseDouble(alturaStr);
            int edad = Integer.parseInt(edadStr);

            if (altura <= 0 || peso <= 0) {
                tvResultadoIMC.setText("IMC: --");
                tvClasificacion.setText("Datos inválidos");
                return;
            }

            double imc = peso / (altura * altura);
            String clasificacion = clasificarIMC(imc, edad);
            String prioridad = determinarPrioridad(clasificacion);

            tvResultadoIMC.setText(String.format("IMC: %.1f", imc));
            tvClasificacion.setText("🏥 " + clasificacion + " | " + getEmojiPrioridad(prioridad) + " " + prioridad);

        } catch (NumberFormatException e) {
            tvResultadoIMC.setText("IMC: --");
            tvClasificacion.setText("Verifique los datos ingresados");
        }
    }

    private String clasificarIMC(double imc, int edad) {
        if (edad < 18) {
            if (imc < 16) return "Desnutrición Grave 🚨";
            else if (imc < 18.5) return "Desnutrición Moderada ⚠️";
            else if (imc < 25) return "Peso Normal ✅";
            else if (imc < 30) return "Sobrepeso 📈";
            else return "Obesidad 🔴";
        } else {
            if (imc < 18.5) return "Bajo Peso ⚠️";
            else if (imc < 25) return "Peso Normal ✅";
            else if (imc < 30) return "Sobrepeso 📈";
            else if (imc < 35) return "Obesidad Grado I 🔴";
            else if (imc < 40) return "Obesidad Grado II 🚨";
            else return "Obesidad Grado III 💀";
        }
    }

    private String determinarPrioridad(String clasificacion) {
        if (clasificacion.contains("Grave") || clasificacion.contains("Grado III")) {
            return "PRIORITARIO";
        } else if (clasificacion.contains("Moderada") || clasificacion.contains("Grado II") ||
                clasificacion.contains("Bajo Peso")) {
            return "OBSERVACIÓN";
        } else if (clasificacion.contains("Normal")) {
            return "SANO";
        } else {
            return "OBSERVACIÓN";
        }
    }

    private String getEmojiPrioridad(String prioridad) {
        switch (prioridad) {
            case "PRIORITARIO": return "🔴";
            case "OBSERVACIÓN": return "🟡";
            case "SANO": return "🟢";
            default: return "⚪";
        }
    }


    private boolean validarCamposBasicos() {
        if (etNombrePaciente.getText().toString().trim().isEmpty() ||
                etTipoSangre.getText().toString().trim().isEmpty() ||
                etDireccion.getText().toString().trim().isEmpty() ||
                etPeso.getText().toString().trim().isEmpty() ||
                etAltura.getText().toString().trim().isEmpty() ||
                etEdad.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Complete todos los campos obligatorios del paciente", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validarCamposResponsable() {
        if (etRespNombre.getText().toString().trim().isEmpty() ||
                etRespCedula.getText().toString().trim().isEmpty() ||
                etRespEdad.getText().toString().trim().isEmpty() ||
                etRespTelefono.getText().toString().trim().isEmpty() ||
                etRespRelacion.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Complete todos los campos del responsable", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        etNombrePaciente.setText("");
        etTipoSangre.setText("");
        etDireccion.setText("");
        etPeso.setText("");
        etAltura.setText("");
        etEdad.setText("");
        etEnfermedades.setText("");
        etAlergias.setText("");
        etSintomas.setText("");
        etRespNombre.setText("");
        etRespCedula.setText("");
        etRespEdad.setText("");
        etRespTelefono.setText("");
        etRespRelacion.setText("");
        layoutResponsable.setVisibility(View.GONE);
        tvResultadoIMC.setText("IMC: --");
        tvClasificacion.setText("Complete los datos para calcular");
    }
}