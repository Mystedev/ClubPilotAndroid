package com.example.clubpilot.Player;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.clubpilot.Fan.Contact_Admin;
import com.example.clubpilot.Fan.Fan_Configuration;
import com.example.clubpilot.Fan.News;
import com.example.clubpilot.Login;
import com.example.clubpilot.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Player_Configuration extends AppCompatActivity implements View.OnClickListener {
    Button buttonBack;
    Spinner spinner;
    TextView account;
    // Strings generals
    String logout,catalan,english,spanish,idioma,cancel,hasLogout;
    String alert,loginOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_player_config);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Obtenir el misstage de seleccio de idiooma
        idioma = getResources().getString(R.string.seleccio_idioma);
        english = getResources().getString(R.string.english);
        spanish = getResources().getString(R.string.spanish);
        catalan = getResources().getString(R.string.catalan);
        logout = getResources().getString(R.string.logout);
        cancel = getResources().getString(R.string.cancel);
        hasLogout = getResources().getString(R.string.hasLogout);
        alert = getResources().getString(R.string.alert);
        loginOut = getResources().getString(R.string.loginOut);
        // Elements de la pantalla
        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(this);
        account = findViewById(R.id.textAccount);
        account.setOnClickListener(this);
        // Spinner selector de idioma
        spinner = findViewById(R.id.spinnerIdiomes);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String tipo_usuari = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(Player_Configuration.this, idioma, Toast.LENGTH_SHORT).show();
            }
        });
        // Llista de opcions
        ArrayList<String> items = new ArrayList<>();
        items.add(idioma);
        items.add(english);
        items.add(spanish);
        items.add(catalan);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Mostrar botón "atrás"/izquierdo
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(R.drawable.back_arrow); // tu drawable personalizado

            // Acción al pulsarlo
            toolbar.setNavigationOnClickListener(v -> {
                // Lo que tú quieras hacer, como volver a otra actividad:
                Intent intent = new Intent(Player_Configuration.this, Dashboard.class);
                startActivity(intent);
                finish(); // Opcional, para cerrar esta actividad
            });
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonBack){
            showDialogo();
        }
        if (view.getId() == R.id.textAccount){
            Intent intent = new Intent(this, Account_Configuration.class);
            startActivity(intent);
        }
    }

    public void showDialogo() {
        // Aquest array conte les opcions que es poden seleccionar
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton(logout, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Player_Configuration.this, Login.class);
                startActivity(intent);
                dialog.dismiss();
                Toast.makeText(Player_Configuration.this, hasLogout, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        builder.setTitle(alert);
        builder.setMessage(loginOut);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}