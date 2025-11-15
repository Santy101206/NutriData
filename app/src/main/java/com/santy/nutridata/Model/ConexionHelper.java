package com.santy.nutridata.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexionHelper extends SQLiteOpenHelper {

    public ConexionHelper(Context context) {
        super(context, Constantes.DB_NAME, null, Constantes.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constantes.CREAR_TABLA_USUARIOS);
        db.execSQL(Constantes.CREAR_TABLA_DATOS);
        db.execSQL(Constantes.CREAR_TABLA_FORMULAS_MEDICAS);
        db.execSQL(Constantes.INSERTAR_DATOS_INICIALES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 7) {
            db.execSQL("ALTER TABLE " + Constantes.TABLA_DATOS + " ADD COLUMN " + Constantes.CAMPO_TRATAMIENTO + " TEXT");
        }
        db.execSQL(Constantes.BORRAR_TABLA_USUARIOS_SI_EXISTE);
        db.execSQL(Constantes.BORRAR_TABLA_DATOS_PACIENTE_SI_EXISTE);
        db.execSQL(Constantes.BORRAR_TABLA_FORMULAS_MEDICAS_SI_EXISTE);
        onCreate(db);
    }
}