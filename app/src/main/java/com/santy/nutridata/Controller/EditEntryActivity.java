package com.santy.nutridata.Controller;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.santy.nutridata.Model.*;
import com.santy.nutridata.R;

public class EditEntryActivity extends AppCompatActivity {

    private EditText etNombrePaciente, etTipoSangre, etDireccion, etPeso, etAltura, etEdad;
    private EditText etEnfermedades, etAlergias, etSintomas;
    private EditText etRespNombre, etRespCedula, etRespEdad, etRespTelefono, etRespRelacion;
    private LinearLayout layoutResponsable;
    private Button btnActualizar, btnVolver;
    private ManagerDB managerDB;
    private int entryId;
    private Datos datosActual;
    private TextView tvResultadoIMC, tvClasificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);

        managerDB = new ManagerDB(this);
        entryId = getIntent().getIntExtra("entryId", -1);

        if (entryId == -1) {
            Toast.makeText(this, "Error: ID no válido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();
        cargarDatos();

        etPeso.addTextChangedListener(imcTextWatcher);
        etAltura.addTextChangedListener(imcTextWatcher);
        etEdad.addTextChangedListener(imcTextWatcher);

        etEdad.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                checkEdad();
                calcularIMC();
            }
        });

        btnActualizar.setOnClickListener(v -> actualizarDatos());
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
        btnActualizar = findViewById(R.id.btnActualizar);
        btnVolver = findViewById(R.id.btnVolver);
        layoutResponsable = findViewById(R.id.layoutResponsable);
        etRespNombre = findViewById(R.id.etRespNombre);
        etRespCedula = findViewById(R.id.etRespCedula);
        etRespEdad = findViewById(R.id.etRespEdad);
        etRespTelefono = findViewById(R.id.etRespTelefono);
        etRespRelacion = findViewById(R.id.etRespRelacion);
        tvResultadoIMC = findViewById(R.id.tvResultadoIMC);
        tvClasificacion = findViewById(R.id.tvClasificacion);
    }

    private TextWatcher imcTextWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            calcularIMC();
        }
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    private void checkEdad() {
        try {
            int edad = Integer.parseInt(etEdad.getText().toString());
            if (edad < 18) {
                layoutResponsable.setVisibility(View.VISIBLE);
            } else {
                layoutResponsable.setVisibility(View.GONE);
                limpiarResponsable();
            }
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

    private void cargarDatos() {
        datosActual = managerDB.obtenerPorId(entryId);
        if (datosActual != null) {
            etNombrePaciente.setText(datosActual.getNombrePaciente());
            etTipoSangre.setText(datosActual.getTipoSangre());
            etDireccion.setText(datosActual.getDireccion());
            etPeso.setText(String.valueOf(datosActual.getPeso()));
            etAltura.setText(String.valueOf(datosActual.getAltura()));
            etEdad.setText(String.valueOf(datosActual.getEdad()));
            etEnfermedades.setText(datosActual.getEnfermedades());
            etAlergias.setText(datosActual.getAlergias());
            etSintomas.setText(datosActual.getSintomas());

            tvResultadoIMC.setText(String.format("IMC: %.1f", datosActual.getImc()));
            tvClasificacion.setText("🏥 " + datosActual.getClasificacion() + " | " +
                    getEmojiPrioridad(datosActual.getPrioridad()) + " " + datosActual.getPrioridad());

            if (datosActual.getEdad() < 18) {
                layoutResponsable.setVisibility(View.VISIBLE);
                etRespNombre.setText(datosActual.getRespNombre());
                etRespCedula.setText(datosActual.getRespCedula());
                etRespEdad.setText(String.valueOf(datosActual.getRespEdad()));
                etRespTelefono.setText(datosActual.getRespTelefono());
                etRespRelacion.setText(datosActual.getRespRelacion());
            } else {
                layoutResponsable.setVisibility(View.GONE);
            }
        } else {
            Toast.makeText(this, "No se pudieron cargar los datos", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void actualizarDatos() {
        try {
            if (!validarCamposBasicos()) return;

            double peso = Double.parseDouble(etPeso.getText().toString().trim());
            double altura = Double.parseDouble(etAltura.getText().toString().trim());
            int edad = Integer.parseInt(etEdad.getText().toString().trim());
            double imc = peso / (altura * altura);
            String clasificacion = clasificarIMC(imc, edad);
            String prioridad = determinarPrioridad(clasificacion);

            Datos nuevos = new Datos();
            nuevos.setId(entryId);
            nuevos.setUsuario(datosActual.getUsuario());
            nuevos.setNombrePaciente(etNombrePaciente.getText().toString().trim());
            nuevos.setTipoSangre(etTipoSangre.getText().toString().trim());
            nuevos.setDireccion(etDireccion.getText().toString().trim());
            nuevos.setPeso(peso);
            nuevos.setAltura(altura);
            nuevos.setEdad(edad);
            nuevos.setEnfermedades(etEnfermedades.getText().toString().trim());
            nuevos.setAlergias(etAlergias.getText().toString().trim());
            nuevos.setSintomas(etSintomas.getText().toString().trim());
            nuevos.setImc(imc);
            nuevos.setClasificacion(clasificacion);
            nuevos.setPrioridad(prioridad);
            nuevos.setMedicoResponsable(datosActual.getMedicoResponsable());
            nuevos.setTratamiento(datosActual.getTratamiento());

            if (edad < 18) {
                if (!validarCamposResponsable()) return;
                nuevos.setRespNombre(etRespNombre.getText().toString().trim());
                nuevos.setRespCedula(etRespCedula.getText().toString().trim());
                nuevos.setRespEdad(Integer.parseInt(etRespEdad.getText().toString().trim()));
                nuevos.setRespTelefono(etRespTelefono.getText().toString().trim());
                nuevos.setRespRelacion(etRespRelacion.getText().toString().trim());
            }

            boolean actualizado = managerDB.actualizarDatosCompleto(nuevos);

            if (actualizado) {
                Toast.makeText(this, "✅ Datos actualizados correctamente\n" + clasificacion, Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "❌ Error al actualizar los datos", Toast.LENGTH_SHORT).show();
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "⚠️ Verifique los valores ingresados", Toast.LENGTH_SHORT).show();
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

    private void limpiarResponsable() {
        etRespNombre.setText("");
        etRespCedula.setText("");
        etRespEdad.setText("");
        etRespTelefono.setText("");
        etRespRelacion.setText("");
    }
}