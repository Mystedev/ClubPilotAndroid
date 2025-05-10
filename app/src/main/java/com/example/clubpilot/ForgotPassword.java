package com.example.clubpilot;

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

import com.example.clubpilot.Fan.RegisterFan;

import java.text.SimpleDateFormat;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    private EditText email,password,newPassword;
    private Button save;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Mostrar botón "atrás"/izquierdo
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(R.drawable.back_arrow); // tu drawable personalizado

            // Acción al pulsarlo
            toolbar.setNavigationOnClickListener(v -> {
                // Lo que tú quieras hacer, como volver a otra actividad:
                Intent intent = new Intent(ForgotPassword.this, Login.class);
                startActivity(intent);
                finish();
            });
        }

        email = findViewById(R.id.emailInut);
        password = findViewById(R.id.newPassword);
        newPassword = findViewById(R.id.newPassword2);
        save  = findViewById(R.id.buttonSave);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buttonSave){
            String email = this.email.getText().toString();
            String password = this.password.getText().toString();
            String newPassword = this.newPassword.getText().toString();

            if (email.isEmpty() || password.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(this, "Rellena tots els camps", Toast.LENGTH_SHORT).show();
            } else {
                if (newPassword.equals(password)) {
                    Thread updateThread = new Thread(() -> {
                        boolean updated = UserDAO.updatePassword(email, newPassword);
                        runOnUiThread(() -> {
                            if (updated) {
                                Toast.makeText(this, "Contrasenya actualitzada correctament", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(this, Login.class));
                                finish();
                            } else {
                                Toast.makeText(this, "No s'ha pogut actualitzar la contrasenya. Comprova l'email.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    });
                    updateThread.start();
                } else {
                    Toast.makeText(this, "Les contrasenyes no coincideixen", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}