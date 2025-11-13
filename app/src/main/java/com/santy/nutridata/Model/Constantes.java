package com.santy.nutridata.Model;

public class Constantes {
    // Base de datos
    public static final String DB_NAME = "nutridata.db";
    public static final int DB_VERSION = 7;

    // TABLA USUARIOS
    public static final String TABLA_USUARIOS = "usuarios";
    public static final String CAMPO_USER = "usuario";
    public static final String CAMPO_PASS = "password";

    // TABLA DATOS
    public static final String TABLA_DATOS = "datos";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_USER_DATOS = "usuario";
    public static final String CAMPO_NOMBRE_PACIENTE = "nombrePaciente";
    public static final String CAMPO_TIPO_SANGRE = "tipoSangre";
    public static final String CAMPO_DIRECCION = "direccion";
    public static final String CAMPO_PESO = "peso";
    public static final String CAMPO_ALTURA = "altura";
    public static final String CAMPO_EDAD = "edad";
    public static final String CAMPO_ENFERMEDADES = "enfermedades";
    public static final String CAMPO_ALERGIAS = "alergias";
    public static final String CAMPO_SINTOMAS = "sintomas";
    public static final String CAMPO_IMC = "imc";
    public static final String CAMPO_CLASIFICACION = "clasificacion";
    public static final String CAMPO_PRIORIDAD = "prioridad";
    public static final String CAMPO_MEDICO_RESPONSABLE = "medicoResponsable";
    public static final String CAMPO_RESP_NOMBRE = "respNombre";
    public static final String CAMPO_RESP_CEDULA = "respCedula";
    public static final String CAMPO_RESP_EDAD = "respEdad";
    public static final String CAMPO_RESP_TELEFONO = "respTelefono";
    public static final String CAMPO_RESP_RELACION = "respRelacion";
    public static final String CAMPO_TRATAMIENTO = "tratamiento";

    // TABLA FORMULAS_MEDICAS
    public static final String TABLA_FORMULAS_MEDICAS = "formulas_medicas";
    public static final String CAMPO_ID_FORMULA = "id_formula";
    public static final String CAMPO_CATEGORIA = "categoria";
    public static final String CAMPO_NOMBRE_ENFERMEDAD = "nombre_enfermedad";
    public static final String CAMPO_MEDICAMENTOS = "medicamentos";
    public static final String CAMPO_DOSIS = "dosis";
    public static final String CAMPO_DURACION = "duracion";
    public static final String CAMPO_RECOMENDACIONES = "recomendaciones";
    public static final String CAMPO_DIETA = "dieta";
    public static final String CAMPO_HIDRATACION = "hidratacion";
    public static final String CAMPO_CONTRAINDICACIONES = "contraindicaciones";

    // Sentencias SQL
    public static final String CREAR_TABLA_USUARIOS =
            "CREATE TABLE " + TABLA_USUARIOS + " (" +
                    CAMPO_USER + " TEXT PRIMARY KEY, " +
                    CAMPO_PASS + " TEXT)";

    public static final String CREAR_TABLA_DATOS =
            "CREATE TABLE " + TABLA_DATOS + " (" +
                    CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CAMPO_USER_DATOS + " TEXT, " +
                    CAMPO_NOMBRE_PACIENTE + " TEXT, " +
                    CAMPO_TIPO_SANGRE + " TEXT, " +
                    CAMPO_DIRECCION + " TEXT, " +
                    CAMPO_PESO + " REAL, " +
                    CAMPO_ALTURA + " REAL, " +
                    CAMPO_EDAD + " INTEGER, " +
                    CAMPO_ENFERMEDADES + " TEXT, " +
                    CAMPO_ALERGIAS + " TEXT, " +
                    CAMPO_SINTOMAS + " TEXT, " +
                    CAMPO_IMC + " REAL, " +
                    CAMPO_CLASIFICACION + " TEXT, " +
                    CAMPO_PRIORIDAD + " TEXT, " +
                    CAMPO_MEDICO_RESPONSABLE + " TEXT, " +
                    CAMPO_RESP_NOMBRE + " TEXT, " +
                    CAMPO_RESP_CEDULA + " TEXT, " +
                    CAMPO_RESP_EDAD + " INTEGER, " +
                    CAMPO_RESP_TELEFONO + " TEXT, " +
                    CAMPO_RESP_RELACION + " TEXT, " +
                    CAMPO_TRATAMIENTO + " TEXT)";

    public static final String CREAR_TABLA_FORMULAS_MEDICAS =
            "CREATE TABLE " + TABLA_FORMULAS_MEDICAS + " (" +
                    CAMPO_ID_FORMULA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CAMPO_CATEGORIA + " TEXT, " +
                    CAMPO_NOMBRE_ENFERMEDAD + " TEXT, " +
                    CAMPO_MEDICAMENTOS + " TEXT, " +
                    CAMPO_DOSIS + " TEXT, " +
                    CAMPO_DURACION + " TEXT, " +
                    CAMPO_RECOMENDACIONES + " TEXT, " +
                    CAMPO_DIETA + " TEXT, " +
                    CAMPO_HIDRATACION + " TEXT, " +
                    CAMPO_CONTRAINDICACIONES + " TEXT)";
}
