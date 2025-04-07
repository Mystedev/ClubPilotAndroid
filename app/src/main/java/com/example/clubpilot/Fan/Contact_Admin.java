package com.example.clubpilot.Fan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.clubpilot.R;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;

public class Contact_Admin extends AppCompatActivity implements View.OnClickListener{
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnSend = findViewById(R.id.buttonSentEmail);
        btnSend.setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Mostrar botón "atrás"/izquierdo
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(R.drawable.back_arrow); // tu drawable personalizado

            // Acción al pulsarlo
            toolbar.setNavigationOnClickListener(v -> {
                // Lo que tú quieras hacer, como volver a otra actividad:
                Intent intent = new Intent(Contact_Admin.this, Fan_Configuration.class);
                startActivity(intent);
                finish(); // Opcional, para cerrar esta actividad
            });
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonSentEmail){
            sendMessage(view);
        }
    }

    public void sendMessage(View view) {
        EditText editText = findViewById(R.id.contact_admin);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Resend resend = new Resend("re_Bd7se8RH_FjrsF9SfDLZh9XG8hQDzYHuP");

                String message = editText.getText().toString(); // Obtenir missatge de l'EditText

                CreateEmailOptions params = CreateEmailOptions.builder()
                        .from("Me <dennys@resend.dev>")
                        .to("moyadeni267@gmail.com", "c.albinya@insjoanbrudieu.cat")
                        .text(message)
                        .subject("Mensaje de Resend")
                        .build();
                Log.d("TAG", "sendMessage: " + params);

                try {
                    CreateEmailResponse data = resend.emails().send(params);
                    System.out.println(data.getId());

                    // Mostrar Toast en el hilo principal
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Contact_Admin.this, "Message sent", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (ResendException e) {
                    e.printStackTrace();

                    // Mostrar Toast de error en el hilo principal
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Contact_Admin.this, "Failed to send message", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        thread.start();
    }

}