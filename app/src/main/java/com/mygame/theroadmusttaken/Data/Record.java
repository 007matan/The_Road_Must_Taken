package com.mygame.theroadmusttaken.Data;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Record implements Comparable {
    private LocalDate recordDate;
    private double lat;
    private double log;
    private int points; // (score + f(distance)

    public Record(LocalDate recordDate, double lat, double log, int points){
        setLat(lat);
        setLog(log);
        setRecordDate(recordDate);
        setPoints(points);
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLog() {
        return log;
    }

    public void setLog(double log) {
        this.log = log;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String toString(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");

        String reVal = "" + this.getPoints() + " " + dtf.format(this.getRecordDate()) + " " +
                this.lat + " " + this.log;
        return reVal;
    }

    @Override
    public int compareTo(Object r) {
        int comparePoints = ((Record)r).getPoints();
        return this.getPoints()-comparePoints;
    }
}
