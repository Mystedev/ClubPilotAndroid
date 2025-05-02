package com.example.clubpilot;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.os.LocaleListCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.clubpilot.Fan.Fan_Configuration;
import com.example.clubpilot.Fan.News;
import com.example.clubpilot.Fan.RegisterFan;
import com.example.clubpilot.PSP.ClubXML;
import com.example.clubpilot.PSP.EsdevenimentXML;
import com.example.clubpilot.PSP.NoticiaXML;
import com.example.clubpilot.PSP.TipusEsdevenimentXML;
import com.example.clubpilot.PSP.UsuariXML;
import com.example.clubpilot.Player.Dashboard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Login extends AppCompatActivity implements View.OnClickListener {
    Button buttonlogin,registerAsFan;
    TextView forgotPassword;
    //Button buttonforgotpassword;
    Spinner user;
    // Strings
    String tipusUsuari,player,fan,errorMessage;
    // DB
    Conection conn;
    Connection con;
    ResultSet rs;
    String str;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 💡 Configurar el idioma ANTES de cargar el layout
        SharedPreferences sharedPref = getSharedPreferences("config", MODE_PRIVATE);
        String savedLanguage = sharedPref.getString("idioma", "en");
        LocaleListCompat appLocale = LocaleListCompat.forLanguageTags(savedLanguage);
        AppCompatDelegate.setApplicationLocales(appLocale);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        setSupportActionBar(findViewById(R.id.toolbar));
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
        // Configurar tema de la app
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM); // -> Tema del sistema

        // Llista d'opcions de jugadors
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


        //conn = new Conection();
        //connect();
    }
    public void connect() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try{
                con = Conection.CONN();
                if (con == null) {
                    str = "Error connecting to database";
                } else {
                    String query = "SELECT nom FROM Usuari WHERE id = 1";
                    Statement stmt = con.createStatement();
                    rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        String nom = rs.getString("nom");
                        str = nom;
                    } else {
                        str = "No se encontró el usuario.";
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            runOnUiThread(() -> {
                try{
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
            });
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonLogin){
            if(user.getSelectedItem().toString().equals(tipusUsuari)){
                //Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
                connect();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.idioma, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String selectedLanguage;
        // Obtener el idioma seleccionado

        if (item.getItemId() == R.id.englishMenu) {
            selectedLanguage = "en";
        } else if (item.getItemId() == R.id.catalanMenu) {
            selectedLanguage = "ca";
        } else if (item.getItemId() == R.id.spanishMenu) {
            selectedLanguage = "es";
        } else {
            selectedLanguage = "en";
        }

        // Guardar en SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("config", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("idioma", selectedLanguage);
        editor.apply();

        // Cambiar idioma
        LocaleListCompat appLocale = LocaleListCompat.forLanguageTags(selectedLanguage);
        AppCompatDelegate.setApplicationLocales(appLocale);

        // Reiniciar la actividad
        recreate();

        return super.onOptionsItemSelected(item);
    }

}