package com.example.sena.materias;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Joalar on 07/11/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    public final static String DB_NAME = "mydb";
    public final static int DB_VERSION = 1;

    //Constructor
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //Evento de creación de base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    //Evento de actualización de base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}