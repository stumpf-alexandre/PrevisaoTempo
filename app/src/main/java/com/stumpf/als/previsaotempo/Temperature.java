package com.stumpf.als.previsaotempo;

import java.io.Serializable;

public class Temperature implements Serializable {
    private String date;
    private String week;
    private int max;
    private int min;
    private String description;

    public Temperature(String date, String week, int max, int min, String description){
        this.date = date;
        this.week = week;
        this.max = max;
        this.min = min;
        this.description = description;
    }

    public String getDate() {
        return R.string.data + "\n" + date.toString();
    }

    public String getWeek() {
        return R.string.semana + "\n" + week.toString();
    }

    public int getMax() {
        return Integer.parseInt(R.string.max + "\n" + R.drawable.arrow_up + String.valueOf(max));
    }

    public int getMin() {
        return Integer.parseInt(R.string.min + "\n" + R.drawable.arrow_down + String.valueOf(min));
    }

    public String getDescription() {
        return R.string.description + "\n" + description.toString();
    }
}