package com.example.clubpilot;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LanguageScreen extends AppCompatActivity implements View.OnClickListener {
    Button buttonStart;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.language_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        buttonStart = findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(this);
        // Boto de login

    }

    @Override
    public void onClick(View view) {
        // Iniciar sessio amb un idioma sleccionat i moure a la pantalla de login
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}