package com.santy.nutridata.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Environment;

import java.io.File;
import java.util.List;

import jxl.Workbook;
import jxl.write.WritableWorkbook;
import jxl.write.WritableSheet;
import jxl.write.Label;
import jxl.write.Number;

import androidx.appcompat.app.AppCompatActivity;

import com.santy.nutridata.Model.Datos;
import com.santy.nutridata.Model.ManagerDB;
import com.santy.nutridata.R;



public class MenuActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private Button btnAddData, btnHistory, btnFormulasMedicas, btnExcel, btnLogout;
    private String username;
    private ManagerDB managerDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnAddData = findViewById(R.id.btnAddData);
        btnHistory = findViewById(R.id.btnHistory);
        btnFormulasMedicas = findViewById(R.id.btnFormulasMedicas);
        btnLogout = findViewById(R.id.btnLogout);
        btnExcel = findViewById(R.id.btnExcel);

        username = getIntent().getStringExtra("username");
        tvWelcome.setText("Bienvenido, Dr. " + username);

        managerDb = new ManagerDB(this);


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

        btnExcel.setOnClickListener(v -> {
            List<Datos> datosPacientes = managerDb.obtenerTodosPacientes();
            exportarExcel(datosPacientes);
        });
    }

    private void exportarExcel(List<Datos> lista) {
        try {
            // Ruta: Descargas
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(path, "pacientes.xls");

            WritableWorkbook workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet("Pacientes", 0);

            // Encabezados
            String[] columnas = {
                    "ID", "Usuario", "Nombre Paciente", "Tipo Sangre", "Direccion",
                    "Peso", "Altura", "Edad", "Enfermedades", "Alergias",
                    "Sintomas", "IMC", "Clasificacion", "Prioridad",
                    "Medico Responsable", "Resp Nombre", "Resp Cedula",
                    "Resp Edad", "Resp Telefono", "Resp Relacion",
                    "Tratamiento"
            };

            // Agregar encabezados
            for (int i = 0; i < columnas.length; i++) {
                sheet.addCell(new Label(i, 0, columnas[i]));
            }

            // Agregar datos
            int rowIndex = 1;

            for (Datos d : lista) {
                sheet.addCell(new Number(0, rowIndex, d.getId()));
                sheet.addCell(new Label(1, rowIndex, d.getUsuario()));
                sheet.addCell(new Label(2, rowIndex, d.getNombrePaciente()));
                sheet.addCell(new Label(3, rowIndex, d.getTipoSangre()));
                sheet.addCell(new Label(4, rowIndex, d.getDireccion()));
                sheet.addCell(new Number(5, rowIndex, d.getPeso()));
                sheet.addCell(new Number(6, rowIndex, d.getAltura()));
                sheet.addCell(new Number(7, rowIndex, d.getEdad()));
                sheet.addCell(new Label(8, rowIndex, d.getEnfermedades()));
                sheet.addCell(new Label(9, rowIndex, d.getAlergias()));
                sheet.addCell(new Label(10, rowIndex, d.getSintomas()));
                sheet.addCell(new Number(11, rowIndex, d.getImc()));
                sheet.addCell(new Label(12, rowIndex, d.getClasificacion()));
                sheet.addCell(new Label(13, rowIndex, d.getPrioridad()));
                sheet.addCell(new Label(14, rowIndex, d.getMedicoResponsable()));
                sheet.addCell(new Label(15, rowIndex, d.getRespNombre()));
                sheet.addCell(new Label(16, rowIndex, d.getRespCedula()));
                sheet.addCell(new Number(17, rowIndex, d.getRespEdad()));
                sheet.addCell(new Label(18, rowIndex, d.getRespTelefono()));
                sheet.addCell(new Label(19, rowIndex, d.getRespRelacion()));
                sheet.addCell(new Label(20, rowIndex, d.getTratamiento()));

                rowIndex++;
            }

            workbook.write();
            workbook.close();

            Toast.makeText(this, "Archivo exportado en Descargas: " + file.getPath(), Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al exportar: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


}