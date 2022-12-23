package com.mygame.theroadmusttaken.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.mygame.theroadmusttaken.R;
import com.mygame.theroadmusttaken.SharedPreferences.RecordSP;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        getSupportActionBar().hide();


        RecordSP.init(this);
        //Check
        //RecordSP.getInstance().putString("1", "walla");

        /*  //The First and Only arr insert
        RecordList recordArr = new RecordList();
        Date date = new Date();
        LocalDate localDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            localDate = LocalDate.now();
        }
        recordArr.setName("My_Playlist");
        recordArr.getRecords().add(new Record(localDate, 33, 34, 13));
        recordArr.getRecords().add(new Record(localDate, 31, 34, 23));
        recordArr.getRecords().add(new Record(localDate, 37, 34, 43));
        recordArr.getRecords().add(new Record(localDate, 30, 33, 83));
        recordArr.getRecords().add(new Record(localDate, 33, 35, 33));
        recordArr.getRecords().add(new Record(localDate, 33, 32, 43));
        recordArr.getRecords().add(new Record(localDate, 33, 22, 63));
        recordArr.getRecords().add(new Record(localDate, 33, 39, 53));
        recordArr.getRecords().add(new Record(localDate, 33, 32, 53));
        recordArr.getRecords().add(new Record(localDate, 33, 34, 53));

        String recordListJson = new Gson().toJson(recordArr);
        RecordSP.getInstance().putString("SP_KEY_RECORD_LIST", recordListJson);
         */

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 3000);

    }
}
