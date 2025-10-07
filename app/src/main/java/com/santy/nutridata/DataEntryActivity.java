package com.santy.nutridata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.*;

public class DataEntryActivity extends AppCompatActivity {

    DbHelper db;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DbHelper(this);
        username = getIntent().getStringExtra("username");
        if (username == null) username = "Usuario";

        ScrollView sv = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        int dp = (int) (16 * getResources().getDisplayMetrics().density);
        root.setPadding(dp, dp, dp, dp);

        TextView tvTitle = new TextView(this);
        tvTitle.setText("Ingresar datos - " + username);
        tvTitle.setTextSize(18f);
        root.addView(tvTitle);

        final EditText etNombre = new EditText(this);
        etNombre.setHint("Nombre completo");
        root.addView(etNombre);

        final EditText etFechaNac = new EditText(this);
        etFechaNac.setHint("Fecha de nacimiento (dd/mm/aaaa)");
        root.addView(etFechaNac);

        final EditText etPeso = new EditText(this);
        etPeso.setHint("Peso (kg)");
        etPeso.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        root.addView(etPeso);

        final EditText etTalla = new EditText(this);
        etTalla.setHint("Talla (m)");
        etTalla.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        root.addView(etTalla);

        final EditText etDireccion = new EditText(this);
        etDireccion.setHint("Dirección");
        root.addView(etDireccion);

        final EditText etTelefono = new EditText(this);
        etTelefono.setHint("Teléfono");
        etTelefono.setInputType(InputType.TYPE_CLASS_PHONE);
        root.addView(etTelefono);

        final CheckBox cbMenor = new CheckBox(this);
        cbMenor.setText("¿Es menor de edad?");
        root.addView(cbMenor);

        final EditText etRespNombre = new EditText(this);
        etRespNombre.setHint("Nombre del responsable");
        etRespNombre.setVisibility(View.GONE);
        root.addView(etRespNombre);

        final EditText etRespTelefono = new EditText(this);
        etRespTelefono.setHint("Teléfono del responsable");
        etRespTelefono.setInputType(InputType.TYPE_CLASS_PHONE);
        etRespTelefono.setVisibility(View.GONE);
        root.addView(etRespTelefono);

        final EditText etRespCedula = new EditText(this);
        etRespCedula.setHint("Cédula del responsable");
        etRespCedula.setInputType(InputType.TYPE_CLASS_NUMBER);
        etRespCedula.setVisibility(View.GONE);
        root.addView(etRespCedula);

        cbMenor.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int vis = isChecked ? View.VISIBLE : View.GONE;
            etRespNombre.setVisibility(vis);
            etRespTelefono.setVisibility(vis);
            etRespCedula.setVisibility(vis);
        });

        final EditText etNota = new EditText(this);
        etNota.setHint("Nota (opcional)");
        etNota.setMinLines(2);
        root.addView(etNota);

        Button btnSave = new Button(this);
        btnSave.setText("Guardar datos");
        root.addView(btnSave);

        sv.addView(root);
        setContentView(sv);

        btnSave.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String fechaNac = etFechaNac.getText().toString().trim();
            String pesoS = etPeso.getText().toString().trim();
            String tallaS = etTalla.getText().toString().trim();
            String direccion = etDireccion.getText().toString().trim();
            String telefono = etTelefono.getText().toString().trim();
            boolean esMenor = cbMenor.isChecked();
            String respNombre = etRespNombre.getText().toString().trim();
            String respTelefono = etRespTelefono.getText().toString().trim();
            String respCedula = etRespCedula.getText().toString().trim();
            String nota = etNota.getText().toString().trim();

            if (nombre.isEmpty() || fechaNac.isEmpty() || pesoS.isEmpty() || tallaS.isEmpty()) {
                Toast.makeText(this, "Completa los campos obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            double peso, talla;
            try {
                peso = Double.parseDouble(pesoS);
                talla = Double.parseDouble(tallaS);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Peso y talla deben ser números", Toast.LENGTH_SHORT).show();
                return;
            }

            if (esMenor && (respNombre.isEmpty() || respTelefono.isEmpty() || respCedula.isEmpty())) {
                Toast.makeText(this, "Completa los datos del responsable", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = db.addEntry(username, nombre, fechaNac, peso, talla, direccion, telefono,
                    esMenor, respNombre, respTelefono, respCedula);

            if (inserted) {
                Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
