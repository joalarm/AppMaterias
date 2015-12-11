package com.example.sena.materias;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MisMateriasActivity extends AppCompatActivity implements EncabezadoFragment.OnFragmentInteractionListener {

    private DBManager dbManager;
    private ListView lstViewMaterias;
    private Cursor cursorLstMaterias;
    private SimpleCursorAdapter simpleAdapter;
    SharedPreferences spref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_materias);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        dbManager = new DBManager(this);
        spref = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        if(spref.getInt("id",-1)==-1)
        {
            Intent intent = new Intent(MisMateriasActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if(spref.getInt("rol",-1)==1) {
            cargar_materias_alumno();
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Intent para inscribir materias
                }
            });
        }
        if(spref.getInt("rol",-1)==2){
            cargar_materias_profesor();
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Intent para crear materia
                }
            });
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void cargar_materias_profesor(){
        cursorLstMaterias = dbManager.consultaAsignaturasProfesor(spref.getInt("id",0));
        String [] from = new String[] {"nombre", "_id"};
        int [] to = new int[] {R.id.txt1, R.id.txt2};
        simpleAdapter = new SimpleCursorAdapter(this, R.layout.listamaterias, cursorLstMaterias,from, to, 0);
        lstViewMaterias = (ListView) findViewById(R.id.lstMaterias);
        lstViewMaterias.setAdapter(simpleAdapter);
    }

    public void cargar_materias_alumno(){
        cursorLstMaterias = dbManager.consultaInscripciones(spref.getInt("id",0));
        String [] from = new String[] {"nombre_asignatura", "nombre_profesor"};
        int [] to = new int[] {R.id.txt1, R.id.txt2};
        simpleAdapter = new SimpleCursorAdapter(this, R.layout.listamaterias, cursorLstMaterias,from, to, 0);
        lstViewMaterias = (ListView) findViewById(R.id.lstMaterias);
        lstViewMaterias.setAdapter(simpleAdapter);
    }
}
