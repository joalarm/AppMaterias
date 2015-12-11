package com.example.sena.materias;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Joalar on 07/11/2015.
 */
public class DBManager {

    private DataBaseHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context)
    {
        helper = new DataBaseHelper(context);
        db = helper.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys = ON;");
    }

    public long insertarUsuario(int id, String password, String nombre, String apellido, String email,
                                String f_nacimiento, int programa, int rol, File foto )
    {
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("contraseña", password);
        values.put("nombre", nombre);
        values.put("apellido", apellido);
        values.put("email", email);
        values.put("fecha_nacimiento", f_nacimiento);
        values.put("programa", programa);
        values.put("rol", rol);
        return db.insert("tbl_usuario",null,values);
    }

    public long insertarAsignatura(int id, String nombre, int profesor, String aula,
                              int programa)
    {
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("nombre", nombre);
        values.put("profesor", profesor);
        values.put("aula", aula);
        values.put("programa", programa);
        return db.insert("tbl_asignatura",null,values);
    }

    public long insertarAsistencia(String fecha, Boolean asistio, int asignatura,
                              int usuario)
    {
        ContentValues values = new ContentValues();
        values.put("fecha", fecha);
        values.put("asistio", asistio);
        values.put("asignatura", asignatura);
        values.put("usuario", usuario);
        return db.insert("tbl_asistencia",null,values);
    }

    public long insertarInscripcion(int usuario, int asignatura, int nota)
    {
        ContentValues values = new ContentValues();
        values.put("asignatura", asignatura);
        values.put("usuario", usuario);
        values.put("nota", nota);
        return db.insert("tbl_inscripcion",null,values);
    }

    public long insertarNota(double nota1, double nota2, double nota3)
    {
        ContentValues values = new ContentValues();
        values.put("nota1", nota1);
        values.put("nota2", nota2);
        values.put("nota3", nota3);
        return db.insert("tbl_nota",null,values);
    }

    public long insertarPrograma(int id, String nombre, String duracion)
    {
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("nombre", nombre);
        values.put("duracion", duracion);
        return db.insert("tbl_programa",null,values);
    }


    public Cursor consultaAsignaturasProfesor(int id)
    {
        String tableName = "asignaturas";
        String [] columns = new String[] {"id as _id", "nombre"};
        String selection = "profesor = ?";
        String [] selectionArgs = new String[]{String.valueOf(id)};
        return db.query(tableName, columns, selection, selectionArgs, null, null, null);
    }

    public Cursor consultaUsuarioLogin(String id, String contrasena)
    {
        String tableName = "usuarios";
        String selection = "id=? and contraseña=?";
        String [] selectionArgs = new String[]{id, contrasena};
        return db.query(tableName,null,selection,selectionArgs,null,null,null);
    }

    public Cursor consultaProgramas()
    {
        String tableName = "tbl_programa";
        String [] columns = new String[] {"id as _id", "nombre"};
        return db.query(tableName,columns,null,null,null,null,null);
    }

    public Cursor consultaRoles()
    {
        String tableName = "tbl_rol";
        String [] columns = new String[] {"id as _id", "nombre"};
        return db.query(tableName,columns,null,null,null,null,null);
    }

    public Cursor consultaInscripciones(int id)
    {
        String tableName = "inscripciones";
        String [] columns = new String[] {"id as _id", "nombre_asignatura", "nombre_profesor"};
        String selection = "usuario=?";
        String [] selectionArgs = new String[]{String.valueOf(id)};
        return db.query(tableName,columns,selection,selectionArgs,null,null,null);
    }
    /*@NonNull
    private ContentValues getContentValues(String [] datos) {
        ContentValues values = new ContentValues();
        for(int i=0; i<datos.length; i++)
        {
        values.put(cnNombre,Nombre);
        values.put(cnApellido,Apellido);
        values.put(cnDirección,Dirección);
        values.put(cnEdad,Edad);
        return values;
    }



    public Cursor cursorConsultaAprendices(String parametro, String valor)
    {
        String[] columns = new String[]{cnId,cnNombre,cnApellido,cnDirección,cnEdad};
        return db.query(nombreTabla,columns,parametro+"=?",new String[]{valor},null,null,null);
    }

    public int eliminar(String parametro, String valor)
    {
        return db.delete(nombreTabla, parametro+"=?", new String[]{valor});
    }

    public int actualizar(String columnap, String parametro, String columnav, String valor)
    {
        ContentValues values = new ContentValues();
        values.put(columnav, valor);
        return db.update(nombreTabla,values,columnap+"=?",new String[]{parametro});
    }*/
}
