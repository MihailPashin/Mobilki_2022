package com.example.var6;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class State {

    private String name; // название
    private String description;  // описание
    private String date;  // Дата
    private String time;  // Время
    private int priority;  // Время

    public State(String name, String description, String date, String time, int priority){
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
        this.priority = priority;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        if (priority > 0 && priority < 3)
            this.priority = priority;
    }
}
