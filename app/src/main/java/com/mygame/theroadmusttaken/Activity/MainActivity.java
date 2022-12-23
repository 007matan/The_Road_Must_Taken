package com.mygame.theroadmusttaken.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;

import com.google.android.material.button.MaterialButton;
import com.mygame.theroadmusttaken.R;

public class MainActivity extends AppCompatActivity {

    private static final String SENSORS_STRING = "SENSORS";
    private static final String BUTTONS_STRING = "BUTTONS";

    private MaterialButton main_MB_Play;
    private MaterialButton main_MB_Top_Ten;
    private RadioButton main_RB_Easy;
    private RadioButton main_RB_Medium;
    private RadioButton main_RB_Sensors;
    private RadioButton main_RB_Buttons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        initViews();

        Bundle extras = getIntent().getExtras();


    }

    private void initViews() {
        main_MB_Play.setOnClickListener(v -> clickedPlay());
        main_MB_Top_Ten.setOnClickListener(v->clickedTopTen());
    }

    private void clickedPlay() {
        Intent intent;
        String inputType = "";
        if(main_RB_Easy.isChecked()){
            intent = new Intent(MainActivity.this, EasyDiffyActivity.class);

        }
        else{
            intent = new Intent(MainActivity.this, MiddleDiffyActivity.class);
        }
        if(main_RB_Sensors.isChecked()){
            inputType= SENSORS_STRING;
        }
        else{
            inputType= BUTTONS_STRING;
        }
        intent.putExtra("input_type",inputType);
        startActivity(intent);
        finish();
    }

    private void clickedTopTen() {
        Intent intent = new Intent(MainActivity.this, Activity_Record_Panel.class);
        startActivity(intent);
    }

    private void findViews() {
         main_MB_Play = findViewById(R.id.main_MB_Play);
         main_MB_Top_Ten = findViewById(R.id.main_MB_Top_Ten);
         main_RB_Easy = findViewById(R.id.main_RB_Easy);
         main_RB_Medium = findViewById(R.id.main_RB_Medium);
         main_RB_Sensors = findViewById(R.id.main_RB_Sensors);
         main_RB_Buttons = findViewById(R.id.main_RB_Buttons);
    }
}