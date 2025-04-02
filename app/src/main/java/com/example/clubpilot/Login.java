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
import com.example.clubpilot.Fan.RegisterFan;
import com.example.clubpilot.PSP.ClubXML;
import com.example.clubpilot.PSP.EsdevenimentXML;
import com.example.clubpilot.PSP.NoticiaXML;
import com.example.clubpilot.PSP.TipusEsdevenimentXML;
import com.example.clubpilot.PSP.UsuariXML;
import com.example.clubpilot.Player.Dashboard;

import java.util.ArrayList;

public class Login extends AppCompatActivity implements View.OnClickListener {
    Button buttonlogin;
    TextView forgotPassword,registerAsFan;
    //Button buttonforgotpassword;
    Spinner user;
    // Strings
    String tipusUsuari,player,fan,errorMessage;
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
        // Strings de la pantalla de login
        tipusUsuari = getResources().getString(R.string.tipo_usuari);
        player = getResources().getString(R.string.player);
        fan = getResources().getString(R.string.fan);
        errorMessage = getResources().getString(R.string.errorSelectUser);
        // Boto de login
        buttonlogin = findViewById(R.id.buttonLogin);
        buttonlogin.setOnClickListener(this);
        // Boto de recuperar contrasenya
        forgotPassword = findViewById(R.id.forgotPassowrd);
        forgotPassword.setOnClickListener(this);
        // Boto de registrar-se com a fan
        registerAsFan = findViewById(R.id.registerfan);
        registerAsFan.setOnClickListener(this);
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
                Toast.makeText(Login.this, tipusUsuari, Toast.LENGTH_SHORT).show();
            }
        });
        // Llista d'opcions d'idiomes
        ArrayList<String> items = new ArrayList<>();
        items.add(tipusUsuari);
        items.add(player);
        items.add(fan);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        user.setAdapter(adapter);
        // Descarrega de la informacio de la base de dades
        ClubXML df = new ClubXML();
        NoticiaXML nf = new NoticiaXML();
        UsuariXML uf = new UsuariXML();
        EsdevenimentXML ef = new EsdevenimentXML();
        TipusEsdevenimentXML tf = new TipusEsdevenimentXML();
        /*Thread thread = new Thread(df);
        thread.start();
        ClubXML fd = null;
        try {
            thread.join();
            fd = df;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(fd.connected){
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonLogin){
            if(user.getSelectedItem().toString().equals(tipusUsuari)){
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            } else if (user.getSelectedItem().toString().equals(fan)){
                Intent intent = new Intent(this, News.class);
                startActivity(intent);
            } else if (user.getSelectedItem().toString().equals(player)){
                Intent intent = new Intent(this, Dashboard.class);
                startActivity(intent);
            }
        }
        if (view.getId() == R.id.forgotPassowrd){
            Intent intent = new Intent(this, ForgotPassword.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.registerfan){
            Intent intent = new Intent(this, RegisterFan.class);
            startActivity(intent);
        }
    }

}