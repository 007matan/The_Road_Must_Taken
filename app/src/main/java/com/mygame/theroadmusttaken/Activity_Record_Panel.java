package com.mygame.theroadmusttaken;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Activity_Record_Panel extends AppCompatActivity {
    private Fragment_Lists fragment_lists;
    private MapFragment fragment_maps;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_panel);

        fragment_lists = new Fragment_Lists();
        fragment_maps = new MapFragment();

        fragment_lists.setCallBack_recordsProtocol(callBack_recordsProtocol);

        getSupportFragmentManager().beginTransaction().add(R.id.panel_LAY_list, fragment_lists).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.panel_LAY_map, fragment_maps).commit();

    }

    CallBack_RecordsProtocol callBack_recordsProtocol = new CallBack_RecordsProtocol() {
        @Override
        public void recordP(Record record) {
            showRecordLocation(record);
        }
    };
    void showRecordLocation(Record record){
        double lat = record.getLat();
        double log = record.getLog();
        int points = record.getPoints();
        fragment_maps.markerLocation(lat, log, points);
    }
}
