package com.example.clubpilot;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.clubpilot.Fan.News;
import com.example.clubpilot.Player.Dashboard;

import java.util.ArrayList;

public class Login extends AppCompatActivity implements View.OnClickListener {
    Button buttonlogin;
    TextView forgotPassword;
    //Button buttonforgotpassword;
    Spinner user;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Boto de login
        buttonlogin = findViewById(R.id.buttonLogin);
        buttonlogin.setOnClickListener(this);
        // Boto de recuperar contrasenya
        forgotPassword = findViewById(R.id.forgotPassowrd);
        forgotPassword.setOnClickListener(this);
        // Spinner per seleccionar el tipus d'usuari
        user = findViewById(R.id.spinner);
        // Afegir un itemSelectedListener en el Spinner per obtenir el tipus d'usuari seleccionat
        user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String tipo_usuari = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(Login.this, "Selecciona un tipus d'usuari", Toast.LENGTH_SHORT).show();
            }
        });

        ArrayList<String> items = new ArrayList<>();
        items.add("Selecciona un tipus d'usuari");
        items.add("Jugador");
        items.add("Aficionat");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        user.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonLogin){
            if(user.getSelectedItem().toString().equals("Selecciona un tipus d'usuari")){
                Toast.makeText(this, "Selecciona un tipus d'usuari per poder accedir", Toast.LENGTH_SHORT).show();
            } else if (user.getSelectedItem().toString().equals("Aficionat")){
                Intent intent = new Intent(this, News.class);
                startActivity(intent);
            } else if (user.getSelectedItem().toString().equals("Jugador")){
                Intent intent = new Intent(this, Dashboard.class);
                startActivity(intent);
            }
        }
        if (view.getId() == R.id.forgotPassowrd){
            Intent intent = new Intent(this, ForgotPassword.class);
            startActivity(intent);
        }
    }
}