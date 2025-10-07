package com.santy.nutridata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "nutridata.db";
    private static final int DB_VERSION = 3;

    // Tabla usuarios
    private static final String TABLE_USERS = "users";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";

    // Tabla datos
    private static final String TABLE_DATA = "data";
    public static final String COL_ID = "id";
    public static final String COL_USER = "username";
    public static final String COL_NOMBRE = "nombre";
    public static final String COL_FECHA = "fechaNacimiento";
    public static final String COL_PESO = "peso";
    public static final String COL_TALLA = "talla";
    public static final String COL_DIRECCION = "direccion";
    public static final String COL_TELEFONO = "telefono";
    public static final String COL_RESPONSABLE = "responsable";
    public static final String COL_RESP_NOMBRE = "respNombre";
    public static final String COL_RESP_TEL = "respTelefono";
    public static final String COL_RESP_CED = "respCedula";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla usuarios
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " (" +
                COL_USERNAME + " TEXT PRIMARY KEY, " +
                COL_PASSWORD + " TEXT)");

        // Crear tabla datos
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_DATA + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USER + " TEXT, " +
                COL_NOMBRE + " TEXT, " +
                COL_FECHA + " TEXT, " +
                COL_PESO + " REAL, " +
                COL_TALLA + " REAL, " +
                COL_DIRECCION + " TEXT, " +
                COL_TELEFONO + " TEXT, " +
                COL_RESPONSABLE + " INTEGER, " +
                COL_RESP_NOMBRE + " TEXT, " +
                COL_RESP_TEL + " TEXT, " +
                COL_RESP_CED + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Si subes versión, borra y crea (simple)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        onCreate(db);
    }

    // ---------- Usuarios ----------
    public boolean userExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT 1 FROM " + TABLE_USERS + " WHERE " + COL_USERNAME + "=?",
                new String[]{username});
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }

    public boolean insertUser(String username, String password) {
        if (userExists(username)) return false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USERNAME, username);
        cv.put(COL_PASSWORD, password);
        long res = db.insert(TABLE_USERS, null, cv);
        return res != -1;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT 1 FROM " + TABLE_USERS +
                        " WHERE " + COL_USERNAME + "=? AND " + COL_PASSWORD + "=?",
                new String[]{username, password});
        boolean ok = c.moveToFirst();
        c.close();
        return ok;
    }

    // ---------- Datos ----------
    public boolean addEntry(String username, String nombre, String fecha, double peso, double talla,
                            String direccion, String telefono, boolean responsable,
                            String respNombre, String respTelefono, String respCedula) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USER, username);
        cv.put(COL_NOMBRE, nombre);
        cv.put(COL_FECHA, fecha);
        cv.put(COL_PESO, peso);
        cv.put(COL_TALLA, talla);
        cv.put(COL_DIRECCION, direccion);
        cv.put(COL_TELEFONO, telefono);
        cv.put(COL_RESPONSABLE, responsable ? 1 : 0);
        cv.put(COL_RESP_NOMBRE, respNombre);
        cv.put(COL_RESP_TEL, respTelefono);
        cv.put(COL_RESP_CED, respCedula);
        long res = db.insert(TABLE_DATA, null, cv);
        return res != -1;
    }

    // Devuelve cursor con los registros del usuario ordenados por id descendente (más recientes arriba)
    public Cursor getEntries(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_DATA + " WHERE " + COL_USER + "=? ORDER BY " + COL_ID + " DESC",
                new String[]{username});
    }

    // Obtener por id
    public Cursor getEntryById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_DATA + " WHERE " + COL_ID + "=?",
                new String[]{String.valueOf(id)});
    }

    // Actualizar registro
    public boolean updateEntry(int id, String nombre, String fecha, double peso, double talla,
                               String direccion, String telefono,
                               boolean responsable, String respNombre, String respTelefono, String respCedula) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NOMBRE, nombre);
        cv.put(COL_FECHA, fecha);
        cv.put(COL_PESO, peso);
        cv.put(COL_TALLA, talla);
        cv.put(COL_DIRECCION, direccion);
        cv.put(COL_TELEFONO, telefono);
        cv.put(COL_RESPONSABLE, responsable ? 1 : 0);
        cv.put(COL_RESP_NOMBRE, respNombre);
        cv.put(COL_RESP_TEL, respTelefono);
        cv.put(COL_RESP_CED, respCedula);
        int res = db.update(TABLE_DATA, cv, COL_ID + "=?", new String[]{String.valueOf(id)});
        return res > 0;
    }
}
