package com.example.sena.materias;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        cargar_db();
        dbManager = new DBManager(this);
        SharedPreferences spref = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        if(spref.getInt("id",-1)!= -1)
        {
            Intent intent = new Intent(LoginActivity.this,MisMateriasActivity.class);
            startActivity(intent);
            overridePendingTransition(android.support.v7.appcompat.R.anim.abc_fade_in, R.anim.abc_fade_out);
            finish();
        }
    }

    private void cargar_db() {
        DataBaseHelper myDbHelper;
        new DataBaseHelper(this);
        myDbHelper = new DataBaseHelper(this);
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        }catch(SQLException sqle){

        }
    }

    public void iniciar_sesion(View v){
        EditText identity = (EditText) findViewById(R.id.edtTextIdentity);
        EditText password = (EditText) findViewById(R.id.edtTextPassword);
        Cursor usuario = dbManager.consultaUsuarioLogin(
                identity.getText().toString(),password.getText().toString());
        if(usuario.getCount() == 1)
        {
            usuario.moveToFirst();
            SharedPreferences spref = getSharedPreferences("usuario", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = spref.edit();
            editor.putInt("id", usuario.getInt(0));
            editor.putString("nombre", usuario.getString(1));
            editor.putString("apellido", usuario.getString(2));
            editor.putString("email", usuario.getString(3));
            editor.putString("nacimiento", usuario.getString(4));
            editor.putInt("programa", usuario.getInt(5));
            editor.putString("programa_nombre", usuario.getString(10));
            editor.putInt("rol", usuario.getInt(6));
            editor.putString("rol_nombre", usuario.getString(9));
            editor.commit();
            Toast.makeText(this,"Bienvenido: "+usuario.getString(1),Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this,MisMateriasActivity.class);
            startActivity(intent);
            overridePendingTransition(android.support.v7.appcompat.R.anim.abc_fade_in, R.anim.abc_fade_out);
            finish();
        } else {
            Toast.makeText(this,"El usuario no existe",Toast.LENGTH_LONG).show();
        }
    }

    public void registrar_usuario(View v){
        EditText identity = (EditText) findViewById(R.id.edtTextIdentity);
        EditText password = (EditText) findViewById(R.id.edtTextPassword);
        Intent intent = new Intent(LoginActivity.this,RegistroActivity.class);
        intent.putExtra("identity",identity.getText().toString());
        intent.putExtra("password", password.getText().toString());
        startActivity(intent);
        overridePendingTransition(android.support.v7.appcompat.R.anim.abc_fade_in, R.anim.abc_fade_out);
    }
}
