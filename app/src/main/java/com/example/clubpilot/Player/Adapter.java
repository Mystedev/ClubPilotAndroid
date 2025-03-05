package com.example.clubpilot.Player;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clubpilot.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    ArrayList<Event> listEvents;
    Event events = new Event("");

    public Adapter(ArrayList<Event> listEvents) {
        this.listEvents = listEvents;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_screen_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       // events = listEvents.get(position);
        holder.description.setText(listEvents.get(position).getDescription());
        holder.data.setText(listEvents.get(position).getDate().toString());
    }

    @Override
    public int getItemCount() {
        return listEvents.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView  description, data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.description);
            data = itemView.findViewById(R.id.date);
        }
    }
}
