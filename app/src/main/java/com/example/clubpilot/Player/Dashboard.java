package com.example.clubpilot.Player;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clubpilot.PSP.EsdevenimentXML;
import com.example.clubpilot.PSP.NoticiaXML;
import com.example.clubpilot.R;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Event> listEvents;
    TextView welcome, dorsal, disponibilidad, posicion;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        setSupportActionBar(findViewById(R.id.toolbar));
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.listEvents);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        listEvents = new ArrayList<Event>();
//        listEvents.add(new Event("Partido vs FCB"));
//        listEvents.add(new Event("Partido vs RM"));
//        listEvents.add(new Event("Evento bienvenida"));
//        listEvents.add(new Event("Evento debut ??"));
//        listEvents.add(new Event("Entreno liga"));
//        listEvents.add(new Event("Partido vs PSG"));
//        listEvents.add(new Event("Evento debut ??"));
//        listEvents.add(new Event("Partido preparación"));
//        listEvents.add(new Event("Entrenamiento publico"));
        listEvents = new ArrayList<>();

        Adapter adapter = new Adapter(listEvents);
        // Iniciar animacio de la llista
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        recyclerView.startAnimation(animation);

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.scheduleLayoutAnimation();
        // Iniciar adapter
        recyclerView.setAdapter(adapter);

        downloadFromServer(); // luego puedes descargar los eventos

        welcome = findViewById(R.id.welcome);
        dorsal = findViewById(R.id.dorsal);
        disponibilidad = findViewById(R.id.available);
        posicion = findViewById(R.id.posicio);

        // Mostra dades del jugador
        Intent intent = getIntent();
        String name = intent.getStringExtra("username");
        // Mostrar el text de benvinguda
        String textWelcome = getString(R.string.welcome);
        welcome.setText(textWelcome + " " + name);

    }

    public void downloadFromServer(){
        NoticiaXML df = new NoticiaXML();
        EsdevenimentXML ef = new EsdevenimentXML();
        Thread thread = new Thread(df);
        Thread thread2 = new Thread(ef);
        thread.start();
        thread2.start();

        try {
            thread.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(df.connected && ef.connected){
            Toast.makeText(this, "Connected to files", Toast.LENGTH_SHORT).show();

            ArrayList<Event> events = (ArrayList<Event>) EsdevenimentXML.parseEsdevenimentsXML();
            runOnUiThread(() -> {
                listEvents.addAll(events);
                recyclerView.getAdapter().notifyDataSetChanged();
            });

        } else {
            Toast.makeText(this, "Error descargando archivos", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        }
        return super.onOptionsItemSelected(item);
    }

}