package com.mygame.theroadmusttaken.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mygame.theroadmusttaken.Protocol.CallBack_RecordsProtocol;
import com.mygame.theroadmusttaken.Fragment.Fragment_Lists;
import com.mygame.theroadmusttaken.Fragment.MapFragment;
import com.mygame.theroadmusttaken.R;
import com.mygame.theroadmusttaken.Data.Record;

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

        /**
         * CallBack_RecordsProtocol function - here it activate inner class function called
         * showRecordLocation
         * @param record - and pass it to the inner class function / do what inside recordP()
         */
        @Override
        public void recordP(Record record) {
            showRecordLocation(record);
        }
    };

    /**
     *
     * The function activate marker function at mapfragment with the record values
     *
     * @param record Record to show
     */
    void showRecordLocation(Record record){
        double lat = record.getLat();
        double log = record.getLog();
        int points = record.getPoints();
        fragment_maps.markerLocation(lat, log, points);
    }
}
