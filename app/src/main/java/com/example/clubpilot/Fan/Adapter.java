package com.example.clubpilot.Fan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.clubpilot.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    ArrayList<CardNew> listNews;
    CardNew news;

    public Adapter(ArrayList<CardNew> listPersones) {
        this.listNews = listPersones;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_screen_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        //holder.foto.setImageResource(R.drawable.ic_launcher_background);
        news = listNews.get(position);
        holder.title.setText(news.getTitle());
        holder.description.setText(news.getDescription());
        holder.image.setImageResource(news.getImage());
    }

    @Override
    public int getItemCount() {
        return listNews.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleNew);
            description = itemView.findViewById(R.id.descriptionNew);
            image = itemView.findViewById(R.id.imageNew);
        }
    }
}
