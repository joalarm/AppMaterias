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
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {

    private DBManager dbManager;
    private Spinner programa;
    private Spinner rol;
    private Cursor cursor;
    private SimpleCursorAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
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
        Bundle extras = getIntent().getExtras();
        if(extras!= null)
        {
            EditText identity = (EditText)findViewById(R.id.edtTextIdentity);
            EditText password = (EditText)findViewById(R.id.edtTextPassword);
            identity.setText(extras.getString("identity"));
            password.setText(extras.getString("password"));
        }
        dbManager = new DBManager(this);
        cargar_programas();
        cargar_roles();
    }

    private void cargar_programas() {
        cursor = dbManager.consultaProgramas();
        String [] from = new String[] {"nombre"};
        int [] to = new int[] {android.R.id.text1};
        simpleAdapter = new SimpleCursorAdapter(this, R.layout.support_simple_spinner_dropdown_item, cursor,from, to, 0);
        programa = (Spinner) findViewById(R.id.spinnerPrograma);
        programa.setAdapter(simpleAdapter);
    }

    private void cargar_roles() {
        cursor = dbManager.consultaRoles();
        String [] from = new String[] {"nombre"};
        int [] to = new int[] {android.R.id.text1};
        simpleAdapter = new SimpleCursorAdapter(this, R.layout.support_simple_spinner_dropdown_item, cursor,from, to, 0);
        rol = (Spinner) findViewById(R.id.spinnerRol);
        rol.setAdapter(simpleAdapter);
    }

    public void registrar(View v) {
        EditText nombre = (EditText)findViewById(R.id.edtTextNombre);
        EditText apellido = (EditText)findViewById(R.id.edtTextApellido);
        EditText email = (EditText)findViewById(R.id.edtTextEmail);
        EditText identity = (EditText)findViewById(R.id.edtTextIdentity);
        EditText password = (EditText)findViewById(R.id.edtTextPassword);
        programa = (Spinner) findViewById(R.id.spinnerPrograma);
        rol = (Spinner) findViewById(R.id.spinnerRol);
        long res = dbManager.insertarUsuario(Integer.parseInt(identity.getText().toString()),password.getText().toString(),
                nombre.getText().toString(),apellido.getText().toString(),email.getText().toString(),
                "01-01-1900",(int) programa.getSelectedItemId(),(int) rol.getSelectedItemId(),null);
        if(res != -1)
        {
            SharedPreferences spref = getSharedPreferences("usuario", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = spref.edit();
            editor.putInt("id", Integer.parseInt(identity.getText().toString()));
            editor.putString("nombre", nombre.getText().toString());
            editor.putString("apellido", apellido.getText().toString());
            editor.putString("email", email.getText().toString());
            editor.putString("nacimiento", "01-01-1900");
            editor.putInt("programa", (int) programa.getSelectedItemId());
            editor.putString("programa_nombre", ((Cursor)programa.getSelectedItem()).getString(1));
            editor.putInt("rol", (int) rol.getSelectedItemId());
            editor.putString("rol_nombre", ((Cursor)rol.getSelectedItem()).getString(1));
            editor.commit();
            Toast.makeText(this, "Bienvenido: " + nombre.getText().toString(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(RegistroActivity.this,MisMateriasActivity.class);
            startActivity(intent);
            overridePendingTransition(android.support.v7.appcompat.R.anim.abc_fade_in, R.anim.abc_fade_out);
            finish();
        } else {
            Toast.makeText(this, "Se ha producido un error", Toast.LENGTH_LONG).show();
        }
    }

}
