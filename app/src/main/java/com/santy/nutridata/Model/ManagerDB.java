package com.santy.nutridata.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class ManagerDB {

    private ConexionHelper conexionHelper;

    public ManagerDB(Context context) {
        conexionHelper = new ConexionHelper(context);
    }

    // REGISTRAR USUARIO
    public boolean registrarUsuario(String usuario, String pass) {
        SQLiteDatabase db = conexionHelper.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery(
                    "SELECT * FROM " + Constantes.TABLA_USUARIOS + " WHERE " + Constantes.CAMPO_USER + "=?",
                    new String[]{usuario});
            boolean existe = cursor.getCount() > 0;
            cursor.close();
            if (existe) return false;

            ContentValues values = new ContentValues();
            values.put(Constantes.CAMPO_USER, usuario);
            values.put(Constantes.CAMPO_PASS, pass);
            long id = db.insert(Constantes.TABLA_USUARIOS, null, values);
            return id > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }

    // VERIFICAR LOGIN
    public boolean verificarUsuario(String usuario, String pass) {
        SQLiteDatabase db = conexionHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(
                    "SELECT * FROM " + Constantes.TABLA_USUARIOS + " WHERE " + Constantes.CAMPO_USER + "=? AND " + Constantes.CAMPO_PASS + "=?",
                    new String[]{usuario, pass});
            return cursor.getCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
    }

    // INSERTAR DATOS
    public boolean insertarDatos(Datos datos) {
        SQLiteDatabase db = conexionHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(Constantes.CAMPO_USER_DATOS, datos.getUsuario());
            values.put(Constantes.CAMPO_NOMBRE_PACIENTE, datos.getNombrePaciente());
            values.put(Constantes.CAMPO_TIPO_SANGRE, datos.getTipoSangre());
            values.put(Constantes.CAMPO_DIRECCION, datos.getDireccion());
            values.put(Constantes.CAMPO_PESO, datos.getPeso());
            values.put(Constantes.CAMPO_ALTURA, datos.getAltura());
            values.put(Constantes.CAMPO_EDAD, datos.getEdad());
            values.put(Constantes.CAMPO_ENFERMEDADES, datos.getEnfermedades());
            values.put(Constantes.CAMPO_ALERGIAS, datos.getAlergias());
            values.put(Constantes.CAMPO_SINTOMAS, datos.getSintomas());
            values.put(Constantes.CAMPO_IMC, datos.getImc());
            values.put(Constantes.CAMPO_CLASIFICACION, datos.getClasificacion());
            values.put(Constantes.CAMPO_PRIORIDAD, datos.getPrioridad());
            values.put(Constantes.CAMPO_MEDICO_RESPONSABLE, datos.getMedicoResponsable());
            values.put(Constantes.CAMPO_RESP_NOMBRE, datos.getRespNombre());
            values.put(Constantes.CAMPO_RESP_CEDULA, datos.getRespCedula());
            values.put(Constantes.CAMPO_RESP_EDAD, datos.getRespEdad());
            values.put(Constantes.CAMPO_RESP_TELEFONO, datos.getRespTelefono());
            values.put(Constantes.CAMPO_RESP_RELACION, datos.getRespRelacion());
            values.put(Constantes.CAMPO_TRATAMIENTO, datos.getTratamiento());

            long id = db.insert(Constantes.TABLA_DATOS, null, values);
            return id > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }

    // ACTUALIZAR DATOS
    public boolean actualizarDatosCompleto(Datos datos) {
        SQLiteDatabase db = conexionHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(Constantes.CAMPO_NOMBRE_PACIENTE, datos.getNombrePaciente());
            values.put(Constantes.CAMPO_TIPO_SANGRE, datos.getTipoSangre());
            values.put(Constantes.CAMPO_DIRECCION, datos.getDireccion());
            values.put(Constantes.CAMPO_PESO, datos.getPeso());
            values.put(Constantes.CAMPO_ALTURA, datos.getAltura());
            values.put(Constantes.CAMPO_EDAD, datos.getEdad());
            values.put(Constantes.CAMPO_ENFERMEDADES, datos.getEnfermedades());
            values.put(Constantes.CAMPO_ALERGIAS, datos.getAlergias());
            values.put(Constantes.CAMPO_SINTOMAS, datos.getSintomas());
            values.put(Constantes.CAMPO_IMC, datos.getImc());
            values.put(Constantes.CAMPO_CLASIFICACION, datos.getClasificacion());
            values.put(Constantes.CAMPO_PRIORIDAD, datos.getPrioridad());
            values.put(Constantes.CAMPO_RESP_NOMBRE, datos.getRespNombre());
            values.put(Constantes.CAMPO_RESP_CEDULA, datos.getRespCedula());
            values.put(Constantes.CAMPO_RESP_EDAD, datos.getRespEdad());
            values.put(Constantes.CAMPO_RESP_TELEFONO, datos.getRespTelefono());
            values.put(Constantes.CAMPO_RESP_RELACION, datos.getRespRelacion());
            values.put(Constantes.CAMPO_TRATAMIENTO, datos.getTratamiento());

            int rowsAffected = db.update(Constantes.TABLA_DATOS, values,
                    Constantes.CAMPO_ID + "=?", new String[]{String.valueOf(datos.getId())});
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }

    // ACTUALIZAR SOLO TRATAMIENTO
    public boolean actualizarTratamiento(int idPaciente, String tratamiento) {
        SQLiteDatabase db = conexionHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(Constantes.CAMPO_TRATAMIENTO, tratamiento);
            int rowsAffected = db.update(Constantes.TABLA_DATOS, values,
                    Constantes.CAMPO_ID + "=?", new String[]{String.valueOf(idPaciente)});
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }

    // OBTENER POR ID
    public Datos obtenerPorId(int id) {
        SQLiteDatabase db = conexionHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + Constantes.TABLA_DATOS + " WHERE " + Constantes.CAMPO_ID + "=?", new String[]{String.valueOf(id)});
            if (cursor.moveToFirst()) {
                Datos d = new Datos();
                d.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Constantes.CAMPO_ID)));
                d.setUsuario(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_USER_DATOS)));
                d.setNombrePaciente(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_NOMBRE_PACIENTE)));
                d.setTipoSangre(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_TIPO_SANGRE)));
                d.setDireccion(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_DIRECCION)));
                d.setPeso(cursor.getDouble(cursor.getColumnIndexOrThrow(Constantes.CAMPO_PESO)));
                d.setAltura(cursor.getDouble(cursor.getColumnIndexOrThrow(Constantes.CAMPO_ALTURA)));
                d.setEdad(cursor.getInt(cursor.getColumnIndexOrThrow(Constantes.CAMPO_EDAD)));
                d.setEnfermedades(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_ENFERMEDADES)));
                d.setAlergias(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_ALERGIAS)));
                d.setSintomas(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_SINTOMAS)));
                d.setImc(cursor.getDouble(cursor.getColumnIndexOrThrow(Constantes.CAMPO_IMC)));
                d.setClasificacion(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_CLASIFICACION)));
                d.setPrioridad(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_PRIORIDAD)));
                d.setMedicoResponsable(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_MEDICO_RESPONSABLE)));
                d.setRespNombre(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_RESP_NOMBRE)));
                d.setRespCedula(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_RESP_CEDULA)));
                d.setRespEdad(cursor.getInt(cursor.getColumnIndexOrThrow(Constantes.CAMPO_RESP_EDAD)));
                d.setRespTelefono(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_RESP_TELEFONO)));
                d.setRespRelacion(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_RESP_RELACION)));
                d.setTratamiento(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_TRATAMIENTO)));
                return d;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
    }

    // OBTENER TODOS LOS PACIENTES
    public List<Datos> obtenerTodosPacientes() {
        List<Datos> lista = new ArrayList<>();
        SQLiteDatabase db = conexionHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + Constantes.TABLA_DATOS + " ORDER BY " +
                    Constantes.CAMPO_PRIORIDAD + " DESC, " + Constantes.CAMPO_ID + " DESC", null);
            if (cursor.moveToFirst()) {
                do {
                    Datos d = new Datos();
                    d.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Constantes.CAMPO_ID)));
                    d.setUsuario(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_USER_DATOS)));
                    d.setNombrePaciente(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_NOMBRE_PACIENTE)));
                    d.setTipoSangre(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_TIPO_SANGRE)));
                    d.setDireccion(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_DIRECCION)));
                    d.setPeso(cursor.getDouble(cursor.getColumnIndexOrThrow(Constantes.CAMPO_PESO)));
                    d.setAltura(cursor.getDouble(cursor.getColumnIndexOrThrow(Constantes.CAMPO_ALTURA)));
                    d.setEdad(cursor.getInt(cursor.getColumnIndexOrThrow(Constantes.CAMPO_EDAD)));
                    d.setEnfermedades(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_ENFERMEDADES)));
                    d.setAlergias(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_ALERGIAS)));
                    d.setSintomas(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_SINTOMAS)));
                    d.setImc(cursor.getDouble(cursor.getColumnIndexOrThrow(Constantes.CAMPO_IMC)));
                    d.setClasificacion(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_CLASIFICACION)));
                    d.setPrioridad(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_PRIORIDAD)));
                    d.setMedicoResponsable(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_MEDICO_RESPONSABLE)));
                    d.setRespNombre(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_RESP_NOMBRE)));
                    d.setRespCedula(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_RESP_CEDULA)));
                    d.setRespEdad(cursor.getInt(cursor.getColumnIndexOrThrow(Constantes.CAMPO_RESP_EDAD)));
                    d.setRespTelefono(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_RESP_TELEFONO)));
                    d.setRespRelacion(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_RESP_RELACION)));
                    d.setTratamiento(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_TRATAMIENTO)));
                    lista.add(d);
                } while (cursor.moveToNext());
            }
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            return lista;
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
    }

    // OBTENER POR USUARIO
    public List<Datos> obtenerPorUsuario(String usuario) {
        List<Datos> lista = new ArrayList<>();
        SQLiteDatabase db = conexionHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + Constantes.TABLA_DATOS + " WHERE " + Constantes.CAMPO_USER_DATOS + "=? ORDER BY " +
                    Constantes.CAMPO_PRIORIDAD + " DESC, " + Constantes.CAMPO_ID + " DESC", new String[]{usuario});
            if (cursor.moveToFirst()) {
                do {
                    Datos d = new Datos();
                    d.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Constantes.CAMPO_ID)));
                    d.setUsuario(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_USER_DATOS)));
                    d.setNombrePaciente(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_NOMBRE_PACIENTE)));
                    d.setTipoSangre(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_TIPO_SANGRE)));
                    d.setDireccion(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_DIRECCION)));
                    d.setPeso(cursor.getDouble(cursor.getColumnIndexOrThrow(Constantes.CAMPO_PESO)));
                    d.setAltura(cursor.getDouble(cursor.getColumnIndexOrThrow(Constantes.CAMPO_ALTURA)));
                    d.setEdad(cursor.getInt(cursor.getColumnIndexOrThrow(Constantes.CAMPO_EDAD)));
                    d.setEnfermedades(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_ENFERMEDADES)));
                    d.setAlergias(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_ALERGIAS)));
                    d.setSintomas(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_SINTOMAS)));
                    d.setImc(cursor.getDouble(cursor.getColumnIndexOrThrow(Constantes.CAMPO_IMC)));
                    d.setClasificacion(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_CLASIFICACION)));
                    d.setPrioridad(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_PRIORIDAD)));
                    d.setMedicoResponsable(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_MEDICO_RESPONSABLE)));
                    d.setRespNombre(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_RESP_NOMBRE)));
                    d.setRespCedula(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_RESP_CEDULA)));
                    d.setRespEdad(cursor.getInt(cursor.getColumnIndexOrThrow(Constantes.CAMPO_RESP_EDAD)));
                    d.setRespTelefono(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_RESP_TELEFONO)));
                    d.setRespRelacion(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_RESP_RELACION)));
                    d.setTratamiento(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_TRATAMIENTO)));
                    lista.add(d);
                } while (cursor.moveToNext());
            }
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            return lista;
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
    }

    // ========== MÉTODOS PARA FORMULAS MÉDICAS ==========

    // OBTENER CATEGORÍAS DE ENFERMEDADES
    public List<String> obtenerCategoriasEnfermedades() {
        List<String> categorias = new ArrayList<>();
        SQLiteDatabase db = conexionHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT DISTINCT " + Constantes.CAMPO_CATEGORIA + " FROM " + Constantes.TABLA_FORMULAS_MEDICAS + " ORDER BY " + Constantes.CAMPO_CATEGORIA, null);
            if (cursor.moveToFirst()) {
                do {
                    categorias.add(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_CATEGORIA)));
                } while (cursor.moveToNext());
            }
            return categorias;
        } catch (Exception e) {
            e.printStackTrace();
            return categorias;
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
    }

    // OBTENER ENFERMEDADES POR CATEGORÍA
    public List<String> obtenerEnfermedadesPorCategoria(String categoria) {
        List<String> enfermedades = new ArrayList<>();
        SQLiteDatabase db = conexionHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT " + Constantes.CAMPO_NOMBRE_ENFERMEDAD + " FROM " + Constantes.TABLA_FORMULAS_MEDICAS + " WHERE " + Constantes.CAMPO_CATEGORIA + "=? ORDER BY " + Constantes.CAMPO_NOMBRE_ENFERMEDAD, new String[]{categoria});
            if (cursor.moveToFirst()) {
                do {
                    enfermedades.add(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_NOMBRE_ENFERMEDAD)));
                } while (cursor.moveToNext());
            }
            return enfermedades;
        } catch (Exception e) {
            e.printStackTrace();
            return enfermedades;
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
    }

    // OBTENER FORMULA MÉDICA COMPLETA
    public FormulaMedica obtenerFormulaPorEnfermedad(String enfermedad) {
        SQLiteDatabase db = conexionHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + Constantes.TABLA_FORMULAS_MEDICAS + " WHERE " + Constantes.CAMPO_NOMBRE_ENFERMEDAD + "=?", new String[]{enfermedad});
            if (cursor.moveToFirst()) {
                FormulaMedica formula = new FormulaMedica();
                formula.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Constantes.CAMPO_ID_FORMULA)));
                formula.setCategoria(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_CATEGORIA)));
                formula.setNombreEnfermedad(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_NOMBRE_ENFERMEDAD)));
                formula.setMedicamentos(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_MEDICAMENTOS)));
                formula.setDosis(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_DOSIS)));
                formula.setDuracion(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_DURACION)));
                formula.setRecomendaciones(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_RECOMENDACIONES)));
                formula.setDieta(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_DIETA)));
                formula.setHidratacion(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_HIDRATACION)));
                formula.setContraindicaciones(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.CAMPO_CONTRAINDICACIONES)));
                return formula;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
    }
}