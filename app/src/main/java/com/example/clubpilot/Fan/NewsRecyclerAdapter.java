package com.example.clubpilot.Fan;

import android.content.Context;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clubpilot.R;
import com.example.clubpilot.UserDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder> {

    private final Context context;
    private final ArrayList<NewsData> newsList;
    private final String currentUsername;
    private final int currentUserId;
    private final Map<Integer, String> followedClubMap; // clave: id_club, valor: nombre

    public NewsRecyclerAdapter(Context context, ArrayList<NewsData> newsList, String username, int userId, Map<Integer, String> followedClubs) {
        this.context = context;
        this.newsList = newsList;
        this.currentUsername = username;
        this.currentUserId = userId;
        this.followedClubMap = new HashMap<>(followedClubs);
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
        holder.title.setText(item.getTitol());
        holder.author.setText(item.getAutor());
        holder.description.setText(item.getDescripcio());
        holder.date.setText(item.getData());

        int clubId = item.getClubId();
        String clubName = item.getImatge(); // fallback por si lo quieres usar también

        boolean isFollowing = followedClubMap.containsKey(clubId);
        holder.btnFollow.setText(isFollowing ? "Dejar de seguir" : "Seguir");

        holder.btnFollow.setOnClickListener(v -> {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                boolean currentlyFollowing = followedClubMap.containsKey(clubId);
                if (currentlyFollowing) {
                    UserDAO.unfollowClub(currentUserId, clubId);
                    followedClubMap.remove(clubId);
                    holder.itemView.post(() -> {
                        holder.btnFollow.setText("Seguir");
                        Toast.makeText(context, "Has dejado de seguir a " + clubName, Toast.LENGTH_SHORT).show();
                    });
                } else {
                    UserDAO.followClub(currentUserId, clubId);
                    followedClubMap.put(clubId, clubName); // podrías usar item.getClubName()
                    holder.itemView.post(() -> {
                        holder.btnFollow.setText("Dejar de seguir");
                        Toast.makeText(context, "Ahora sigues a " + clubName, Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView title, author, description, date, btnFollow;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
            btnFollow = itemView.findViewById(R.id.btnFollow);
        }
    }
}
