package com.santy.nutridata;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.*;

public class EditEntryActivity extends AppCompatActivity {

    DbHelper db;
    int entryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DbHelper(this);
        entryId = getIntent().getIntExtra("entryId", -1);

        ScrollView sv = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        int dp = (int) (16 * getResources().getDisplayMetrics().density);
        root.setPadding(dp, dp, dp, dp);

        final EditText etNombre = new EditText(this);
        etNombre.setHint("Nombre");
        root.addView(etNombre);

        final EditText etFecha = new EditText(this);
        etFecha.setHint("Fecha de nacimiento (dd/mm/aaaa)");
        root.addView(etFecha);

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
        root.addView(etTelefono);

        final CheckBox cbMenor = new CheckBox(this);
        cbMenor.setText("¿Es menor?");
        root.addView(cbMenor);

        final EditText etRespNombre = new EditText(this);
        etRespNombre.setHint("Nombre responsable");
        root.addView(etRespNombre);

        final EditText etRespTelefono = new EditText(this);
        etRespTelefono.setHint("Teléfono responsable");
        root.addView(etRespTelefono);

        final EditText etRespCedula = new EditText(this);
        etRespCedula.setHint("Cédula responsable");
        etRespCedula.setInputType(InputType.TYPE_CLASS_NUMBER);
        root.addView(etRespCedula);

        final EditText etNota = new EditText(this);
        etNota.setHint("Nota (opcional)");
        root.addView(etNota);

        Button btnUpdate = new Button(this);
        btnUpdate.setText("Actualizar");
        root.addView(btnUpdate);

        sv.addView(root);
        setContentView(sv);

        // Cargar datos desde BD
        if (entryId != -1) {
            Cursor c = null;
            try {
                c = db.getEntryById(entryId);
                if (c != null && c.moveToFirst()) {
                    etNombre.setText(c.getString(c.getColumnIndexOrThrow(DbHelper.COL_NOMBRE)));
                    etFecha.setText(c.getString(c.getColumnIndexOrThrow(DbHelper.COL_FECHA)));
                    etPeso.setText(String.valueOf(c.getDouble(c.getColumnIndexOrThrow(DbHelper.COL_PESO))));
                    etTalla.setText(String.valueOf(c.getDouble(c.getColumnIndexOrThrow(DbHelper.COL_TALLA))));
                    etDireccion.setText(c.getString(c.getColumnIndexOrThrow(DbHelper.COL_DIRECCION)));
                    etTelefono.setText(c.getString(c.getColumnIndexOrThrow(DbHelper.COL_TELEFONO)));
                    boolean esMenor = c.getInt(c.getColumnIndexOrThrow(DbHelper.COL_RESPONSABLE)) == 1;
                    cbMenor.setChecked(esMenor);
                    etRespNombre.setText(c.getString(c.getColumnIndexOrThrow(DbHelper.COL_RESP_NOMBRE)));
                    etRespTelefono.setText(c.getString(c.getColumnIndexOrThrow(DbHelper.COL_RESP_TEL)));
                    etRespCedula.setText(c.getString(c.getColumnIndexOrThrow(DbHelper.COL_RESP_CED)));
                    etNota.setText(c.getString(c.getColumnIndexOrThrow("nota")) != null ? c.getString(c.getColumnIndexOrThrow("nota")) : "");
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error cargando registro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                if (c != null) c.close();
            }
        }

        // Inicializar visibilidad de campos de responsable
        int visInit = cbMenor.isChecked() ? View.VISIBLE : View.GONE;
        etRespNombre.setVisibility(visInit);
        etRespTelefono.setVisibility(visInit);
        etRespCedula.setVisibility(visInit);

        cbMenor.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int v = isChecked ? View.VISIBLE : View.GONE;
            etRespNombre.setVisibility(v);
            etRespTelefono.setVisibility(v);
            etRespCedula.setVisibility(v);
        });

        btnUpdate.setOnClickListener(v -> {
            try {
                String nombre = etNombre.getText().toString().trim();
                String fecha = etFecha.getText().toString().trim();
                double peso = Double.parseDouble(etPeso.getText().toString().trim());
                double talla = Double.parseDouble(etTalla.getText().toString().trim());
                String direccion = etDireccion.getText().toString().trim();
                String telefono = etTelefono.getText().toString().trim();
                boolean esMenor = cbMenor.isChecked();
                String respNombre = etRespNombre.getText().toString().trim();
                String respTel = etRespTelefono.getText().toString().trim();
                String respCed = etRespCedula.getText().toString().trim();

                if (esMenor && (respNombre.isEmpty() || respTel.isEmpty() || respCed.isEmpty())) {
                    Toast.makeText(this, "Completa los datos del responsable", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean ok = db.updateEntry(entryId, nombre, fecha, peso, talla, direccion, telefono, esMenor, respNombre, respTel, respCed);
                if (ok) {
                    Toast.makeText(this, "Registro actualizado", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException nfe) {
                Toast.makeText(this, "Peso y talla deben ser números válidos", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
