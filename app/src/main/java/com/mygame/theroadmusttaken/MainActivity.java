package com.mygame.theroadmusttaken;

import static com.mygame.theroadmusttaken.GameManager.MAX_NUM_LIFES;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ShapeableImageView[] main_SIM_MatNodes;


    private ShapeableImageView[] main_IMG_lifes;

    private ExtendedFloatingActionButton main_FB_Right;
    private ExtendedFloatingActionButton main_FB_Left;

    private GameManager gameManager;

    final int DELAY = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        gameManager = new GameManager();
        initViews();
        updateView();

        /*
        final Handler handler = new Handler();
        final int delay = 2000; // 1000 milliseconds == 1 second
        handler.postDelayed(new Runnable() {
            public void run() {
                funcInDelay();
                handler.postDelayed(this, delay);
            }
        }, delay);
         */
        startTimer();
        //for(int i = 0; i < 20; i++)
        //    funcInDelay();
    }

    private Timer timer = new Timer();

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        funcInDelay();
                    }
                });
            }
        }, DELAY, DELAY);
    }

    public void stopTimer() {
        timer.cancel();
    }

    public void funcInDelay(){
        boolean isHeat = false;
        isHeat = gameManager.mainFunction();
        if(isHeat)
            vibrate();
        updateView();
        checkGameOver();
    }

    private void checkGameOver() {
        if(gameManager.getLifes()==0)
            stopTimer();
    }

    private void initViews() {
        main_FB_Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked(DataManager.Car_Direction.RIGHT_DIRECTION);
            }
        });

        main_FB_Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked(DataManager.Car_Direction.LEFT_DIRECTION);
            }
        });
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }


    }



    private void clicked(DataManager.Car_Direction carDirection) {
        int afterLife, currLife = gameManager.getLifes(); //for the retet, boolean is not enough, i need to know beside if turn valid(not out of bounds, also if there is a rock now
        if(gameManager.moveCar(carDirection))
            updateView();
        afterLife = gameManager.getLifes();
        if(currLife > afterLife)
            vibrate();
        checkGameOver();
    }
/*
    private void updateCarView(DataManager.Car_Direction carDirection) {
        if(carDirection == DataManager.Car_Direction.LEFT_DIRECTION)
            main_SIM_MatNodes[gameManager.getDataManager().getIndexOfRacingCar() - 1].setImageResource(R.drawable.ic_racing_car);
        else if(carDirection == DataManager.Car_Direction.RIGHT_DIRECTION)  {
            main_SIM_MatNodes[gameManager.getDataManager().getIndexOfRacingCar() + 1].setImageResource(R.drawable.ic_racing_car);
        }
    }
*/
    ;

    private void updateView()
    {
        int indexRock;
        int indexCoin;
        clearView();
        for(int i = 0; i < gameManager.getIndexesOfRocksArr().size() &&
                gameManager.getIndexesOfRocksArr().get(i) >= 0; i++){
            indexRock = gameManager.getIndexesOfRocksArr().get(i);
            main_SIM_MatNodes[indexRock].setImageResource(R.drawable.ic_rock);
        }
        /*
        for(int i = 0; i < gameManager.getIndexesOfCoinsArr().size(); i++){
            indexCoin = gameManager.getIndexesOfCoinsArr().get(i);
            main_SIM_MatNodes[i].setImageResource(R.drawable.ic_dogecoin);
        }
        */
        main_SIM_MatNodes[gameManager.getDataManager().getIndexOfRacingCar()].setImageResource(R.drawable.ic_racing_car);
        //gameManager.getIndexesOfRocksArr(); - for DONE
        //coins
        //gameManager.getIndexOfCar(); - Done
        //lifes - Done
        int idx;
        for(idx = 0; idx < gameManager.getLifes(); idx++) {
            main_IMG_lifes[idx].setVisibility(View.VISIBLE);
        }
        for(int sidx=gameManager.getLifes(); sidx < MAX_NUM_LIFES; sidx++) {
            main_IMG_lifes[sidx].setVisibility(View.INVISIBLE);
        }
    }

    private void clearView() {
        for(int i = 0; i < main_SIM_MatNodes.length; i++)
        main_SIM_MatNodes[i].setImageResource(0);
    }


    private void findViews() {
        main_FB_Right = findViewById(R.id.main_FB_Right);
        main_FB_Left = findViewById(R.id.main_FB_left);


        main_SIM_MatNodes = new ShapeableImageView[]{
                findViewById(R.id.main_SIM_stIndex),
                findViewById(R.id.main_SIM_ndIndex),
                findViewById(R.id.main_SIM_rdIndex),
                findViewById(R.id.main_SIM_fourthIndex),
                findViewById(R.id.main_SIM_fifthIndex),
                findViewById(R.id.main_SIM_sixthIndex),
                findViewById(R.id.main_SIM_seventhIndex),
                findViewById(R.id.main_SIM_egihthIndex),
                findViewById(R.id.main_SIM_ninthIndex),
                findViewById(R.id.main_SIM_tenthIndex),
                findViewById(R.id.main_SIM_eleventhIndex),
                findViewById(R.id.main_SIM_twelvethIndex),
                findViewById(R.id.main_SIM_thirdteenIndex),
                findViewById(R.id.main_SIM_fourteenIndex),
                findViewById(R.id.main_SIM_fifteenIndex),
                findViewById(R.id.main_SIM_sixteenIndex),
                findViewById(R.id.main_SIM_seventeenIndex),
                findViewById(R.id.main_SIM_eighteenIndex),
                findViewById(R.id.main_SIM_nineteenIndex),
                findViewById(R.id.main_SIM_twenetyIndex),
                findViewById(R.id.main_SIM_twentystIndex)
        };


        main_IMG_lifes = new ShapeableImageView[]{
                findViewById(R.id.main_SIM_stLife),
                findViewById(R.id.main_SIM_ndLife),
                findViewById(R.id.main_SIM_rdLife),
                findViewById(R.id.main_SIM_fourthLife),
                findViewById(R.id.main_SIM_fifthLife)
        };
    }
}