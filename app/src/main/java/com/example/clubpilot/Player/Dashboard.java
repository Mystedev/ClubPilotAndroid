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
import android.widget.Switch;
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
import java.util.List;

public class Dashboard extends AppCompatActivity  {
    RecyclerView recyclerView;
    ArrayList<Event> listEvents;
    TextView welcome, dorsal, disponibilidad, posicion;
    Switch available;

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
        downloadFromServer();

        // Obtener datos del intent
        Intent intent = getIntent();
//        List<Event> receivedEvents = (List<Event>) intent.getSerializableExtra("listEvents");
//        if (receivedEvents != null) {
//            listEvents.clear();
//            listEvents.addAll(receivedEvents);
//        }

        welcome = findViewById(R.id.welcome);
        dorsal = findViewById(R.id.dorsal);
        disponibilidad = findViewById(R.id.available);
        posicion = findViewById(R.id.posicio);
        available = findViewById(R.id.available);

        // Mostra dades del jugador
        String name = intent.getStringExtra("username");
        String dorsal = intent.getStringExtra("playerDorsal");
        int disponibilidad = intent.getIntExtra("playerDisponibilitat",0);
        String posicion = intent.getStringExtra("playerPosicio");

        // Mostrar el text de benvinguda
        String textWelcome = getString(R.string.welcome);
        welcome.setText(textWelcome + " " + name);
        // Mostrar el dorsal
        String textDorsal = getString(R.string.dorsal);
        this.dorsal.setText(textDorsal + " " + dorsal);
        // Mostrar la disponibilidad
        if(disponibilidad == 1){
            available.setChecked(true);
        } else {
            available.setChecked(false);
        }
        // Mostrar la posición
        this.posicion.setText(posicion);
    }

    public void downloadFromServer() {
        EsdevenimentXML ef = new EsdevenimentXML(this);
        Thread thread = new Thread(ef);
        thread.start();

        try {
            thread.join(); // Espera a que termine la descarga
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (ef.connected) {
            Toast.makeText(this, "Connected to files", Toast.LENGTH_SHORT).show();

            List<Event> downloadedEvents = ef.parseEsdevenimentsXML(); // Usa el mismo objeto
            if (downloadedEvents != null) {
                listEvents.clear(); // Limpia lista original
                listEvents.addAll(downloadedEvents); // Agrega los nuevos eventos
                recyclerView.getAdapter().notifyDataSetChanged(); // Notifica al Adapter
            }

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