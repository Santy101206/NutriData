package com.santy.nutridata.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.santy.nutridata.Model.Datos;
import com.santy.nutridata.Model.ManagerDB;
import com.santy.nutridata.R;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private LinearLayout root;
    private ManagerDB managerDB;
    private String username;
    private Spinner spinnerFiltro;
    private Button btnFiltrar, btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        root = findViewById(R.id.rootHistory);
        managerDB = new ManagerDB(this);
        username = getIntent().getStringExtra("username");

        spinnerFiltro = findViewById(R.id.spinnerFiltro);
        btnFiltrar = findViewById(R.id.btnFiltrar);
        btnVolver = findViewById(R.id.btnVolver);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.filtros_prioridad, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFiltro.setAdapter(adapter);

        btnFiltrar.setOnClickListener(v -> cargarHistorialFiltrado());
        btnVolver.setOnClickListener(v -> finish());

        cargarHistorialFiltrado();
    }

    private void cargarHistorialFiltrado() {
        root.removeAllViews();
        int dp = (int) getResources().getDisplayMetrics().density * 8;

        TextView title = new TextView(this);
        title.setText("📋 Historial General de Pacientes");
        title.setTextSize(20f);
        title.setTextColor(0xFF2E7D32);
        title.setPadding(0, 0, 0, dp * 2);
        root.addView(title);

        TextView userInfo = new TextView(this);
        userInfo.setText("👨‍⚕️ Conectado como: Dr. " + username);
        userInfo.setTextSize(14f);
        userInfo.setTextColor(0xFF666666);
        userInfo.setPadding(0, 0, 0, dp);
        root.addView(userInfo);

        List<Datos> lista = managerDB.obtenerTodosPacientes();

        String filtro = spinnerFiltro.getSelectedItem().toString();
        if (!filtro.equals("TODOS")) {
            lista = filtrarLista(lista, filtro);
        }

        if (lista == null || lista.isEmpty()) {
            TextView empty = new TextView(this);
            empty.setText("No hay registros de pacientes disponibles.");
            empty.setPadding(0, dp, 0, dp);
            root.addView(empty);
            return;
        }

        int prioritarios = 0, sanos = 0, observacion = 0;
        for (Datos d : lista) {
            switch (d.getPrioridad()) {
                case "PRIORITARIO": prioritarios++; break;
                case "SANO": sanos++; break;
                case "OBSERVACIÓN": observacion++; break;
            }
        }

        LinearLayout statsLayout = new LinearLayout(this);
        statsLayout.setOrientation(LinearLayout.HORIZONTAL);
        statsLayout.setPadding(dp, dp, dp, dp);
        statsLayout.setBackgroundColor(0xFFE3F2FD);
        statsLayout.setWeightSum(3);

        addStatCard(statsLayout, "🔴 Prioritarios", String.valueOf(prioritarios), 0xFFF44336);
        addStatCard(statsLayout, "🟡 Observación", String.valueOf(observacion), 0xFFFF9800);
        addStatCard(statsLayout, "🟢 Sanos", String.valueOf(sanos), 0xFF4CAF50);

        root.addView(statsLayout);

        for (Datos d : lista) {
            agregarPacienteAlHistorial(d, dp);
        }
    }

    private void addStatCard(LinearLayout layout, String titulo, String valor, int color) {
        int dp8 = dp(8);
        int dp12 = dp(12);

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackgroundColor(color);
        card.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        card.setPadding(dp8, dp12, dp8, dp12);

        TextView tvTitulo = new TextView(this);
        tvTitulo.setText(titulo);
        tvTitulo.setTextSize(12f);
        tvTitulo.setTextColor(0xFFFFFFFF);
        tvTitulo.setGravity(View.TEXT_ALIGNMENT_CENTER);

        TextView tvValor = new TextView(this);
        tvValor.setText(valor);
        tvValor.setTextSize(18f);
        tvValor.setTextColor(0xFFFFFFFF);
        tvValor.setTypeface(tvValor.getTypeface(), android.graphics.Typeface.BOLD);
        tvValor.setGravity(View.TEXT_ALIGNMENT_CENTER);

        card.addView(tvTitulo);
        card.addView(tvValor);
        layout.addView(card);
    }

    private int dp(int value) {
        return (int) (getResources().getDisplayMetrics().density * value);
    }

    private List<Datos> filtrarLista(List<Datos> lista, String filtro) {
        if (filtro.equals("PRIORITARIOS")) {
            lista.removeIf(d -> !d.getPrioridad().equals("PRIORITARIO"));
        } else if (filtro.equals("SANOS")) {
            lista.removeIf(d -> !d.getPrioridad().equals("SANO"));
        } else if (filtro.equals("OBSERVACIÓN")) {
            lista.removeIf(d -> !d.getPrioridad().equals("OBSERVACIÓN"));
        }
        return lista;
    }

    private void agregarPacienteAlHistorial(Datos d, int dp) {
        LinearLayout pacienteLayout = new LinearLayout(this);
        pacienteLayout.setOrientation(LinearLayout.VERTICAL);
        pacienteLayout.setPadding(dp, dp, dp, dp);

        int colorFondo = getColorPrioridad(d.getPrioridad());
        pacienteLayout.setBackgroundColor(colorFondo);
        pacienteLayout.setElevation(4f);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 0, 0, dp * 2);
        pacienteLayout.setLayoutParams(layoutParams);

        LinearLayout headerLayout = new LinearLayout(this);
        headerLayout.setOrientation(LinearLayout.HORIZONTAL);
        headerLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        TextView tvPrioridad = new TextView(this);
        tvPrioridad.setText(getEmojiPrioridad(d.getPrioridad()) + " " + d.getPrioridad());
        tvPrioridad.setTextSize(14f);
        tvPrioridad.setTextColor(0xFF000000);
        tvPrioridad.setTypeface(tvPrioridad.getTypeface(), android.graphics.Typeface.BOLD);
        tvPrioridad.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1
        ));

        TextView tvMedico = new TextView(this);
        tvMedico.setText("👨‍⚕️ " + d.getMedicoResponsable());
        tvMedico.setTextSize(12f);
        tvMedico.setTextColor(0xFF666666);
        tvMedico.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        headerLayout.addView(tvPrioridad);
        headerLayout.addView(tvMedico);
        pacienteLayout.addView(headerLayout);

        TextView tvInfo = new TextView(this);
        String info = "👤 " + d.getNombrePaciente() +
                "\n🎂 " + d.getEdad() + " años | 🩸 " + d.getTipoSangre() +
                "\n⚖️ " + d.getPeso() + " kg | 📏 " + d.getAltura() + " m" +
                "\n📊 IMC: " + String.format("%.1f", d.getImc()) +
                "\n🏥 " + d.getClasificacion();

        if (d.getEnfermedades() != null && !d.getEnfermedades().isEmpty()) {
            info += "\n🤒 Enfermedades: " + d.getEnfermedades();
        }

        if (d.getAlergias() != null && !d.getAlergias().isEmpty()) {
            info += "\n⚠️ Alergias: " + d.getAlergias();
        }

        if (d.getSintomas() != null && !d.getSintomas().isEmpty()) {
            info += "\n🔍 Síntomas: " + d.getSintomas();
        }

        if (d.getTratamiento() != null && !d.getTratamiento().isEmpty()) {
            info += "\n💊 TRATAMIENTO PRESCRITO: ✅";
        } else {
            info += "\n💊 TRATAMIENTO: ❌ Sin tratamiento";
        }

        if (d.getEdad() < 18 && d.getRespNombre() != null && !d.getRespNombre().isEmpty()) {
            info += "\n👨‍👦 Responsable: " + d.getRespNombre() + " (" + d.getRespRelacion() + ")";
        }

        tvInfo.setText(info);
        tvInfo.setTextSize(14f);
        tvInfo.setTextColor(0xFF333333);
        tvInfo.setPadding(0, dp/2, 0, dp);
        pacienteLayout.addView(tvInfo);

        LinearLayout botonesLayout = new LinearLayout(this);
        botonesLayout.setOrientation(LinearLayout.HORIZONTAL);
        botonesLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        Button btnEdit = new Button(this);
        btnEdit.setText("✏️ Editar");
        btnEdit.setBackgroundColor(0xFF2196F3);
        btnEdit.setTextColor(0xFFFFFFFF);
        btnEdit.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1
        ));
        btnEdit.setPadding(dp(8), dp(4), dp(8), dp(4));

        Button btnFormula = new Button(this);
        btnFormula.setText("🧑‍⚕️ Fórmula");
        btnFormula.setBackgroundColor(0xFF4CAF50);
        btnFormula.setTextColor(0xFFFFFFFF);
        btnFormula.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1
        ));
        btnFormula.setPadding(dp(8), dp(4), dp(8), dp(4));

        botonesLayout.addView(btnEdit);
        botonesLayout.addView(btnFormula);
        pacienteLayout.addView(botonesLayout);

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(HistoryActivity.this, EditEntryActivity.class);
            intent.putExtra("entryId", d.getId());
            startActivity(intent);
        });

        btnFormula.setOnClickListener(v -> {
            Intent intent = new Intent(HistoryActivity.this, FormulaMedicaActivity.class);
            intent.putExtra("idPaciente", d.getId());
            intent.putExtra("nombrePaciente", d.getNombrePaciente());
            intent.putExtra("edad", d.getEdad());
            intent.putExtra("peso", d.getPeso());
            intent.putExtra("alergias", d.getAlergias());
            intent.putExtra("username", username);
            startActivity(intent);
        });

        root.addView(pacienteLayout);
    }

    private int getColorPrioridad(String prioridad) {
        switch (prioridad) {
            case "PRIORITARIO": return 0xFFFFEBEE;
            case "OBSERVACIÓN": return 0xFFFFF3E0;
            case "SANO": return 0xFFE8F5E8;
            default: return 0xFFF5F5F5;
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

    @Override
    protected void onResume() {
        super.onResume();
        cargarHistorialFiltrado();
    }
}