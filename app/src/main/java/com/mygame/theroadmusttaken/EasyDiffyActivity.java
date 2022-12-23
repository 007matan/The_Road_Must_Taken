package com.mygame.theroadmusttaken;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static com.mygame.theroadmusttaken.GameManager.MAX_NUM_LIFES;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Timer;
import java.util.TimerTask;

public class EasyDiffyActivity extends AppCompatActivity {

    private ShapeableImageView[] main_SIM_MatNodes;


    private ShapeableImageView[] main_IMG_lives;

    private ExtendedFloatingActionButton main_FB_Right;
    private ExtendedFloatingActionButton main_FB_Left;

    private TextInputEditText main_MTV_score_output;
    private TextInputEditText main_MTV_distance_output;

    private GameManager gameManager;

    private StepDetector stepDetector;

    private String stepDetctorOrder;

    private LocationManager locationManager;
    private LocationListener listener;

    final int DELAY = 1000;

    private int changeDelay;
    public static double lat_Game_Easy = 15.3;
    public static double log_Game_Easy = 34.9;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);

        findViews();



        gameManager = new GameManager(new Easy_Difficulty_Level_Builder(), callBack_location);
        initViews();
        updateView();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            stepDetctorOrder = extras.getString("input_type");
        }
        if(stepDetctorOrder.contains("SENSORS")) {
            stepDetector = new StepDetector(this, callBack_stepsProtocol);
            main_FB_Left.setVisibility(View.INVISIBLE);
            main_FB_Right.setVisibility(View.INVISIBLE);
        }

        //getgpslocation

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat_Game_Easy = location.getLatitude();
                log_Game_Easy = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                //
            }

            @Override
            public void onProviderEnabled(String s) {
                //
            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        changeDelay = DELAY;
        startTimer();
        /*
        for(int i = 0; i < 20; i++)
            funcInDelay();

         */
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 10:
                configure_premission();
                break;
            default:
                break;
        }
    }

    void configure_premission() {
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(EasyDiffyActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EasyDiffyActivity.this,
                ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                request_permission();
            }
        } else {
            // permission has been granted
            locationManager.requestLocationUpdates("gps", 5000, 0, listener);
        }
    }

    private void request_permission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(EasyDiffyActivity.this,
                ACCESS_COARSE_LOCATION)) {

            Snackbar.make(findViewById(R.id.main_LLC_stRow_middle), "Location permission is needed because ...",
                            Snackbar.LENGTH_LONG)
                    .setAction("retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 10);
                            }
                        }
                    })
                    .show();
        } else {
            // permission has not been granted yet. Request it directly.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 10);
            }
        }
    }

    private GameManager.CallBack_location callBack_location = new GameManager.CallBack_location() {
        @Override
        public void shortcut_configure_premission() {
            configure_premission();
        }

        @Override
        public double getLatFromActivity_Game() {
            return lat_Game_Easy;
        }

        @Override
        public double getLogFromActivity_Game() {
            return log_Game_Easy;
        }
    };

    private CallBack_StepsProtocol callBack_stepsProtocol = new CallBack_StepsProtocol() {
        @Override
        public void smallRightStep() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                clicked(DataManager.Car_Direction.RIGHT_DIRECTION);
            }
        }

        @Override
        public void bigRightStep() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                clicked(DataManager.Car_Direction.RIGHT_DIRECTION);
                clicked(DataManager.Car_Direction.RIGHT_DIRECTION);
            }
        }

        @Override
        public void smallLeftStep() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                clicked(DataManager.Car_Direction.LEFT_DIRECTION);
            }
        }

        @Override
        public void bigLeftStep() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                clicked(DataManager.Car_Direction.LEFT_DIRECTION);
                clicked(DataManager.Car_Direction.LEFT_DIRECTION);
            }
        }

        @Override
        public void fasterStep() {
            changeDelay -=200;
        }

        @Override
        public void fastestStep() {
            changeDelay -= 100;
        }

        @Override
        public void lowerStep() {
            changeDelay += 200;
        }

        @Override
        public void lowestStep() {
            changeDelay +=100;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if(stepDetctorOrder.contains("SENSORS")) {
            stepDetector.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(stepDetctorOrder.contains("SENSORS")) {
            stepDetector.stop();
        }
    }

    private Timer timer = new Timer();

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                runOnUiThread(() -> funcInDelay());
            }
        }, this.changeDelay, this.changeDelay);
    }

    public void stopTimer() {
        timer.cancel();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void funcInDelay(){
        boolean isHeat;
        isHeat = gameManager.mainFunction();
        if(isHeat) {
            vibrate();
            MediaPlayer ring= MediaPlayer.create(EasyDiffyActivity.this,R.raw.car_crash);
            ring.start();
        }
        updateView();
        checkGameOver();
    }

    private void checkGameOver() {
        if(gameManager.getLifes()==0) {
            stopTimer();
            Intent intent = new Intent(EasyDiffyActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initViews() {
        main_FB_Right.setOnClickListener(v -> clicked(DataManager.Car_Direction.RIGHT_DIRECTION));

        main_FB_Left.setOnClickListener(v -> clicked(DataManager.Car_Direction.LEFT_DIRECTION));
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



    @RequiresApi(api = Build.VERSION_CODES.O)
    private void clicked(DataManager.Car_Direction carDirection) {
        int afterLife, currLife = gameManager.getLifes(); //for the vibration, boolean is not enough, i need to know beside if turn valid(not out of bounds, also if there is a rock now
        if(gameManager.moveCar(carDirection))
            updateView();
        afterLife = gameManager.getLifes();
        if(currLife > afterLife) {
            vibrate();
            MediaPlayer ring= MediaPlayer.create(EasyDiffyActivity.this,R.raw.car_crash);
            ring.start();
        }
        checkGameOver();
    }

    private void updateView()
    {
        int indexRock;
        int indexCoin;
        clearView();
        for(int i = 0; i < gameManager.getIndexesOfRocksArr().size() &&
                gameManager.getIndexesOfRocksArr().get(i) >= 0; i++){
            indexRock = gameManager.getIndexesOfRocksArr().get(i);
            main_SIM_MatNodes[indexRock].setImageResource(new Rock().getImageRes());

            /*
            if(indexRock < gameManager.getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatCols() *
                    gameManager.getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatCols()) {
                main_SIM_MatNodes[indexRock].setImageResource(new Rock().getImageRes());
            }
             */
        }
        for(int i = 0; i < gameManager.getIndexesOfCoinsArr().size() &&
                gameManager.getIndexesOfCoinsArr().get(i) >= 0; i++){
            indexCoin = gameManager.getIndexesOfCoinsArr().get(i);
            main_SIM_MatNodes[indexCoin].setImageResource(new Coin().getImageRes());
            /*
            if(indexCoin < gameManager.getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatCols() *
                    gameManager.getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatCols()) {
                main_SIM_MatNodes[indexCoin].setImageResource(new Coin().getImageRes());
            }
             */
        }
        main_SIM_MatNodes[gameManager.getDataManager().getIndexOfRacingCar()].setImageResource(new Car().getImageRes());
        //gameManager.getIndexesOfRocksArr(); - for DONE
        //coins
        //gameManager.getIndexOfCar(); - Done
        //lives - Done

        updateLifeView();
        updateScoreView();
        updateDistanceView();
    }

    private void updateDistanceView() {
        main_MTV_distance_output.setText(""+gameManager.getDistance());
    }

    private void updateScoreView() {
        main_MTV_score_output.setText(""+gameManager.getScore());
    }

    private void updateLifeView() {
        int idx;
        for(idx = 0; idx < gameManager.getLifes(); idx++) {
            main_IMG_lives[idx].setVisibility(View.VISIBLE);
        }
        for(int sidx=gameManager.getLifes(); sidx < MAX_NUM_LIFES; sidx++) {
            main_IMG_lives[sidx].setVisibility(View.INVISIBLE);
        }
    }

    private void clearView() {
        for (ShapeableImageView main_sim_matNode : main_SIM_MatNodes)
            main_sim_matNode.setImageResource(0);
    }


    private void findViews() {
        main_MTV_score_output = findViewById(R.id.main_MTV_score_output);
        main_MTV_distance_output = findViewById(R.id.main_MTV_distance_output);

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


        main_IMG_lives = new ShapeableImageView[]{
                findViewById(R.id.main_SIM_stLife),
                findViewById(R.id.main_SIM_ndLife),
                findViewById(R.id.main_SIM_rdLife),
                findViewById(R.id.main_SIM_fourthLife),
                findViewById(R.id.main_SIM_fifthLife)
        };
    }
}
