package com.example.clubpilot.Player;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clubpilot.Login;
import com.example.clubpilot.R;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Event> listEvents;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.listEvents);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listEvents = new ArrayList<Event>();
        listEvents.add(new Event("Partido vs FCB"));
        listEvents.add(new Event("Partido vs RM"));
        listEvents.add(new Event("Evento bienvenida"));
        listEvents.add(new Event("Evento debut ??"));
        listEvents.add(new Event("Entreno liga"));
        listEvents.add(new Event("Partido vs PSG"));
        listEvents.add(new Event("Evento debut ??"));
        listEvents.add(new Event("Partido preparación"));
        listEvents.add(new Event("Entrenamiento publico"));

        Adapter adapter = new Adapter(listEvents);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.config_player) {
            Intent intent = new Intent(this, Player_Configuration.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.logout) {
            showDialogo();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDialogo() {
        // Aquest array conte les opcions que es poden seleccionar
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Dashboard.this, Login.class);
                startActivity(intent);
                dialog.dismiss();
                Toast.makeText(Dashboard.this, "Has tancat sessió", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        builder.setTitle("Alert!");
        builder.setMessage("You're about to logout the session");
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}