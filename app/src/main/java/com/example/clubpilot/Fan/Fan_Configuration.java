package com.example.clubpilot.Fan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.clubpilot.R;

import java.util.ArrayList;

public class Fan_Configuration extends AppCompatActivity implements View.OnClickListener {
    Button buttonBack;

    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fan_configuration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(this);

        spinner = findViewById(R.id.spinnerIdiomes);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String tipo_usuari = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(Fan_Configuration.this, "Selecciona un tipus d'usuari", Toast.LENGTH_SHORT).show();
            }
        });
        ArrayList<String> items = new ArrayList<>();
        items.add("English");
        items.add("Spanish");
        items.add("Catalan");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonBack){
            Intent intent = new Intent(this, News.class);
            Toast.makeText(this, "Configuracio guardada", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }
}