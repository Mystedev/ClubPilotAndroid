package com.example.clubpilot.Fan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListAdapter;
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

import com.example.clubpilot.R;
import com.example.clubpilot.UserDAO;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class News extends AppCompatActivity{
    private RecyclerView recyclerView;
    private NewsRecyclerAdapter adapter;
    private ArrayList<NewsData> newsList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_news);
        setSupportActionBar(findViewById(R.id.toolbar));
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Recuperar la lista del intent
        newsList = (ArrayList<NewsData>) getIntent().getSerializableExtra("listNews");

        if (newsList == null) {
            newsList = new ArrayList<>();
            Log.d("NEWS", "Lista vacía o nula");
        } else {
            Log.d("NEWS", "Tamaño de la lista: " + newsList.size());
        }

        String currentUsername = getIntent().getStringExtra("username");

        if (newsList == null) {
            newsList = new ArrayList<>();
            Log.d("NEWS", "Lista vacía o nula");
        } else {
            Log.d("NEWS", "Tamaño de la lista: " + newsList.size());
        }

        // 2. Configurar el RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 3. Obtener el userId y los clubes seguidos en segundo plano
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            int userId = UserDAO.getUserId(currentUsername);
            Map<Integer, String> followedClubs = UserDAO.getFollowedClubMap(userId); // método actualizado que devuelve Map

            new Handler(Looper.getMainLooper()).post(() -> {
                Toast.makeText(News.this, "Club: " + followedClubs, Toast.LENGTH_SHORT).show();
                Toast.makeText(News.this, "Usuario: " + currentUsername + " - ID: " + userId, Toast.LENGTH_SHORT).show();
                adapter = new NewsRecyclerAdapter(News.this, newsList, currentUsername, userId, followedClubs);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            });
        });
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