package com.example.clubpilot.Fan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.clubpilot.Encriptator;
import com.example.clubpilot.Login;
import com.example.clubpilot.R;
import com.example.clubpilot.UserDAO;
import com.google.android.material.button.MaterialButton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterFan extends AppCompatActivity implements View.OnClickListener {

    Button buttonRegister;
    EditText username,password,nom,cognoms,email;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_fan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Mostrar toolbar personalitzat
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(R.drawable.back_arrow);


            toolbar.setNavigationOnClickListener(v -> {
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
                finish();
            });
        }

        // Elements del formulari
        username = findViewById(R.id.username);
        password = findViewById(R.id.passwordFan);
        nom = findViewById(R.id.nameFan);
        cognoms = findViewById(R.id.cognoms);
        email = findViewById(R.id.email);

        // Boto de registre
        buttonRegister = findViewById(R.id.buttonLogin);
        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonLogin) {
            String usuari = username.getText().toString().trim();
            String contrasenya = password.getText().toString().trim();
            String nomText = nom.getText().toString().trim();
            String cognomsText = cognoms.getText().toString().trim();
            String correu = email.getText().toString().trim();
            // Comprovar que els camps tenen dades
            if (usuari.isEmpty() || contrasenya.isEmpty() || nomText.isEmpty() || cognomsText.isEmpty() || correu.isEmpty()) {
                Toast.makeText(this, "Rellena tots els camps", Toast.LENGTH_SHORT).show(); // Comprovar que els camps tenen dades
            } else {
                String hashedPassword = Encriptator.hashPassword(contrasenya); // Encriptar la contrasenya i pujarla
                setNewAccount(usuari, hashedPassword, nomText, cognomsText, correu); // Registrar un usuari a la base de dades
            }
        }
    }

    // Funcio per registrar un usuari aficionat
    public void setNewAccount(String username, String password, String nom, String cognoms, String email) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            int userId = UserDAO.insertUserAndReturnId(username, password, nom, cognoms, email);
            boolean result = false;
            if (userId != -1) {
                // Registrar l'usuari aficionat
                result = UserDAO.insertAficionat(userId);
            }

            boolean finalResult = result;
            runOnUiThread(() -> {
                if (finalResult) {
                    Toast.makeText(this, "Usuari aficionat registrat correctament", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, Login.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Error en registrar usuari aficionat", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

}