package com.example.clubpilot.Fan;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clubpilot.R;

import java.util.ArrayList;

public class News extends AppCompatActivity{
    RecyclerView recyclerView;
    ArrayList<CardNew> listNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_news);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.listNews);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listNews = new ArrayList<CardNew>();
        listNews.add(new CardNew("FCB","Partido vs FCB", "Partido contra el FCB", R.drawable.fcbarcelona));
        listNews.add(new CardNew("RB","Partido vs RM", "Description2", R.drawable.realmadrid));
        listNews.add(new CardNew("MCU","Evento bienvenida", "Description3", R.drawable.evento));
        listNews.add(new CardNew("LOP","Evento debut ??", "Description4", R.drawable.debut));
        listNews.add(new CardNew("MC","Entreno liga", "Description5", R.drawable.liga));

        Adapter adapter = new Adapter(listNews);
        // Iniciar animacio de la llista
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        recyclerView.startAnimation(animation);

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.scheduleLayoutAnimation();
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.fan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.config_fan) {
            Intent intent = new Intent(this, Fan_Configuration.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}