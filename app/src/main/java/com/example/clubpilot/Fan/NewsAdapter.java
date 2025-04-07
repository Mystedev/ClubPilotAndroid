package com.example.clubpilot.Fan;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.clubpilot.R;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<CardNew> {
    public NewsAdapter(@NonNull Context context, ArrayList<CardNew> listItems) {
        super(context, R.layout.list_item , listItems);
    }

    @NonNull
    @Override
    public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CardNew item = getItem(position);
        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.list_item, null);
        }

        ImageView imageView = convertView.findViewById(R.id.listItem);
        TextView textView = convertView.findViewById(R.id.listName);
        TextView textTime = convertView.findViewById(R.id.time);
        textView.setText(item.getTitle());
        imageView.setImageResource(item.getImage());
        textTime.setText(item.getDate());

        return convertView;
    }
}
