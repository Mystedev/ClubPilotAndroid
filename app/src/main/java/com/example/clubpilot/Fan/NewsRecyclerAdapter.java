package com.example.clubpilot.Fan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clubpilot.R;

import java.util.ArrayList;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder> {

    private final Context context;
    private final ArrayList<NewsData> newsList;

    public NewsRecyclerAdapter(Context context, ArrayList<NewsData> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsData item = newsList.get(position);
        holder.title.setText(item.getAutor());
        holder.date.setText(item.getData());

//        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(context, NewsDetail.class);
//            intent.putExtra("title", item.getTitle());
//            intent.putExtra("description", item.getDescription());
//            intent.putExtra("image", item.getImage());
//            context.startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView title, date;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.listName);
            date = itemView.findViewById(R.id.time);
        }
    }
}
