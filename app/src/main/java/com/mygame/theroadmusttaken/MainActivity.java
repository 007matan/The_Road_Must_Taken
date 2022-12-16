package com.mygame.theroadmusttaken;

import static com.mygame.theroadmusttaken.GameManager.MAX_NUM_LIFES;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.RadioButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

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


    }

    private void initViews() {
        main_MB_Play.setOnClickListener(v -> clickedPlay());
        main_MB_Top_Ten.setOnClickListener(v->clickedTopTen());
    }

    private void clickedPlay() {
        Intent intent;
        if(main_RB_Easy.isChecked()){
            intent = new Intent(MainActivity.this, EasyDiffyActivity.class);
        }
        else{
            intent = new Intent(MainActivity.this, MiddleDiffyActivity.class);
        }
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