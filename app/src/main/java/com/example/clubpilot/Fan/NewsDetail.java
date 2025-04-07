package com.example.clubpilot.Fan;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.clubpilot.R;
import com.example.clubpilot.databinding.ActivityNewsDetailBinding;

public class NewsDetail extends AppCompatActivity {
    ActivityNewsDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewsDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        if(intent != null){
            String title = intent.getStringExtra("title");
            String description = intent.getStringExtra("description");
            int image = intent.getIntExtra("image", 0);

            binding.title.setText(title);
            binding.detailImage.setImageResource(image);
            binding.content.setText(description);
        }
    }
}