package com.santy.nutridata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class HistoryActivity extends AppCompatActivity {

    DbHelper db;
    String username;
    LinearLayout root;
    int dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DbHelper(this);
        username = getIntent().getStringExtra("username");
        if (username == null) username = "";

        ScrollView sv = new ScrollView(this);
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        dp = (int) (16 * getResources().getDisplayMetrics().density);
        root.setPadding(dp, dp, dp, dp);

        sv.addView(root);
        setContentView(sv);

        populateEntries();
    }

    private void populateEntries() {
        root.removeAllViews();

        TextView title = new TextView(this);
        title.setText("Historial - " + username);
        title.setTextSize(18f);
        root.addView(title);

        Cursor cursor = null;
        try {
            cursor = db.getEntries(username);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    final int id = cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.COL_ID));
                    String nombre = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COL_NOMBRE));
                    String fecha = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COL_FECHA));
                    double peso = cursor.getDouble(cursor.getColumnIndexOrThrow(DbHelper.COL_PESO));
                    double talla = cursor.getDouble(cursor.getColumnIndexOrThrow(DbHelper.COL_TALLA));
                    int resp = cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.COL_RESPONSABLE));

                    TextView tv = new TextView(this);
                    String s = "ID: " + id + "\nNombre: " + nombre + "\nNacimiento: " + fecha +
                            "\nPeso: " + peso + " kg\nTalla: " + talla + " m\nMenor: " + (resp == 1 ? "Sí" : "No");
                    tv.setText(s);
                    tv.setPadding(0, 0, 0, dp / 2);
                    root.addView(tv);

                    Button btnEdit = new Button(this);
                    btnEdit.setText("Editar");
                    root.addView(btnEdit);

                    btnEdit.setOnClickListener(v -> {
                        Intent i = new Intent(HistoryActivity.this, EditEntryActivity.class);
                        i.putExtra("entryId", id);
                        startActivity(i);
                    });

                    View separator = new View(this);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, 2);
                    lp.setMargins(0, dp / 4, 0, dp / 4);
                    separator.setLayoutParams(lp);
                    separator.setBackgroundColor(0xFFCCCCCC);
                    root.addView(separator);

                } while (cursor.moveToNext());
            } else {
                TextView empty = new TextView(this);
                empty.setText("No hay registros todavía.");
                root.addView(empty);
            }
        } catch (Exception e) {
            TextView err = new TextView(this);
            err.setText("Error al leer la BD: " + e.getMessage());
            root.addView(err);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // refrescar la lista al volver (no usar recreate())
        populateEntries();
    }
}
