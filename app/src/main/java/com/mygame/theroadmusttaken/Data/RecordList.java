package com.mygame.theroadmusttaken.Data;

import java.util.ArrayList;

public class RecordList {
    private String name = "";
    private ArrayList<Record> records = new ArrayList<>();

    public RecordList() {}

    public String getName() {
        return name;
    }

    public RecordList setName(String name) {
        this.name = name;
        return this;
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    public RecordList setRecords(ArrayList<Record> songs) {
        this.records = records;
        return this;
    }
}
