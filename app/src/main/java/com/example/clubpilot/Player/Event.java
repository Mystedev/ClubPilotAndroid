package com.example.clubpilot.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {
    private String date;
    private String description;
    private final String formatData = "dd/MM/yyyy";
    private SimpleDateFormat sdf = new SimpleDateFormat(formatData);

    public Event( String description) {
        this.date = sdf.format(new Date());
        this.description = description;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}
