package com.mygame.theroadmusttaken;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

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
import com.mygame.theroadmusttaken.Activity.MainActivity;
import com.mygame.theroadmusttaken.Data.Car;
import com.mygame.theroadmusttaken.Data.Coin;
import com.mygame.theroadmusttaken.Manegement.DataManager;
import com.mygame.theroadmusttaken.Manegement.GameManager;
import com.mygame.theroadmusttaken.Data.Middle_Difficulty_Level_Builder;
import com.mygame.theroadmusttaken.Data.Rock;
import com.mygame.theroadmusttaken.Manegement.StepDetector;
import com.mygame.theroadmusttaken.Protocol.CallBack_StepsProtocol;

import java.util.Timer;
import java.util.TimerTask;

public class MiddleDiffyActivity extends AppCompatActivity {

    private ShapeableImageView[] main_SIM_MatNodes_middle;


    private ShapeableImageView[] main_IMG_lives_middle;

    private ExtendedFloatingActionButton main_FB_Right_middle;
    private ExtendedFloatingActionButton main_FB_Left_middle;

    private TextInputEditText main_MTV_score_output_middle;
    private TextInputEditText main_MTV_distance_output_middle;

    private GameManager gameManager;

    private StepDetector stepDetector;

    private String stepDetctorOrder;

    private LocationManager locationManager;
    private LocationListener listener;

    final int DELAY = 500;
    public static double lat_Game_Medium = 30.3;
    public static double log_Game_Medium = 38.9;
    private int chnagedDelay;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_middle);
        findViews();

        gameManager = new GameManager(new Middle_Difficulty_Level_Builder(), callBack_location);

        initViews();
        updateView();

        //if(sensors)

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            stepDetctorOrder = extras.getString("input_type");
        }
        if(stepDetctorOrder.contains("SENSORS")) {
            stepDetector = new StepDetector(this, callBack_stepsProtocol);
            main_FB_Right_middle.setVisibility(View.INVISIBLE);
            main_FB_Left_middle.setVisibility(View.INVISIBLE);
        }


        //getgpslocation

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat_Game_Medium = location.getLatitude();
                log_Game_Medium = location.getLongitude();
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
        chnagedDelay = DELAY;
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
        if (ActivityCompat.checkSelfPermission(MiddleDiffyActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MiddleDiffyActivity.this,
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
        if (ActivityCompat.shouldShowRequestPermissionRationale(MiddleDiffyActivity.this,
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
            return lat_Game_Medium;
        }

        @Override
        public double getLogFromActivity_Game() {
            return log_Game_Medium;
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
            chnagedDelay -=200;
        }

        @Override
        public void fastestStep() {
            chnagedDelay -= 100;
        }

        @Override
        public void lowerStep() {
            chnagedDelay += 200;
        }

        @Override
        public void lowestStep() {
            chnagedDelay +=100;
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
        }, this.chnagedDelay, this.chnagedDelay);
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
            MediaPlayer ring= MediaPlayer.create(MiddleDiffyActivity.this,R.raw.car_crash);
            ring.start();
        }
        updateView();
        checkGameOver();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initViews() {
        main_FB_Right_middle.setOnClickListener(v -> clicked(DataManager.Car_Direction.RIGHT_DIRECTION));

        main_FB_Left_middle.setOnClickListener(v -> clicked(DataManager.Car_Direction.LEFT_DIRECTION));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void clicked(DataManager.Car_Direction carDirection) {
        int afterLife, currLife = gameManager.getLifes(); //for the vibration, boolean is not enough, i need to know beside if turn valid(not out of bounds, also if there is a rock now
        if(gameManager.moveCar(carDirection))
            updateView();
        afterLife = gameManager.getLifes();
        if(currLife > afterLife){
            vibrate();
            MediaPlayer ring= MediaPlayer.create(MiddleDiffyActivity.this,R.raw.car_crash);
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
            if (indexRock < gameManager.getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatCols()*
                    gameManager.getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatRows()) {
                main_SIM_MatNodes_middle[indexRock].setImageResource(new Rock().getImageRes());
            }
        }
        for(int i = 0; i < gameManager.getIndexesOfCoinsArr().size() &&
                gameManager.getIndexesOfCoinsArr().get(i) >= 0; i++){
            indexCoin = gameManager.getIndexesOfCoinsArr().get(i);
            if (indexCoin < gameManager.getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatCols()*
                    gameManager.getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatRows()) {
                main_SIM_MatNodes_middle[indexCoin].setImageResource(new Coin().getImageRes());
            }
        }
        main_SIM_MatNodes_middle[gameManager.getDataManager().getIndexOfRacingCar()].setImageResource(new Car().getImageRes());

        updateLifeView();
        updateScoreView();
        updateDistanceView();
    }

    private void updateDistanceView() {
        main_MTV_distance_output_middle.setText(""+gameManager.getDistance());
    }

    private void updateScoreView() {
        main_MTV_score_output_middle.setText(""+gameManager.getScore());
    }

    private void updateLifeView() {
        int idx;
        for(idx = 0; idx < gameManager.getLifes(); idx++) {
            main_IMG_lives_middle[idx].setVisibility(View.VISIBLE);
        }
        for(int sidx=gameManager.getLifes(); sidx < gameManager.getDataManager().getGameLayout().getDifficultyLevelBuilder().getHeartNum(); sidx++) {
            main_IMG_lives_middle[sidx].setVisibility(View.INVISIBLE);
        }
    }

    private void checkGameOver() {
        if(gameManager.getLifes()==0) {
            stopTimer();
            Intent intent = new Intent(MiddleDiffyActivity.this, MainActivity.class);
            startActivity(intent);
        }
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

    private void clearView() {
        for (ShapeableImageView main_sim_matNode : main_SIM_MatNodes_middle)
            main_sim_matNode.setImageResource(0);
    }

    private void findViews() {
        main_MTV_score_output_middle = findViewById(R.id.main_MTV_score_output_middle);
        main_MTV_distance_output_middle = findViewById(R.id.main_MTV_distance_output_middle);

        main_FB_Right_middle = findViewById(R.id.main_FB_Right_middle);
        main_FB_Left_middle = findViewById(R.id.main_FB_left_middle);


        main_SIM_MatNodes_middle = new ShapeableImageView[]{
                findViewById(R.id.main_SIM_stIndex_middle),
                findViewById(R.id.main_SIM_ndIndex_middle),
                findViewById(R.id.main_SIM_rdIndex_middle),
                findViewById(R.id.main_SIM_fourthIndex_middle),
                findViewById(R.id.main_SIM_fifthIndex_middle),
                findViewById(R.id.main_SIM_sixthIndex_middle),
                findViewById(R.id.main_SIM_seventhIndex_middle),
                findViewById(R.id.main_SIM_egihtIndex_middle),
                findViewById(R.id.main_SIM_ninthIndex_middle),
                findViewById(R.id.main_SIM_tenthIndex_middle),
                findViewById(R.id.main_SIM_eleventhIndex_middle),
                findViewById(R.id.main_SIM_twelvethIndex_middle),
                findViewById(R.id.main_SIM_thirdteenIndex_middle),
                findViewById(R.id.main_SIM_fourteenIndex_middle),
                findViewById(R.id.main_SIM_fifteenIndex_middle),
                findViewById(R.id.main_SIM_sixteenIndex_middle),
                findViewById(R.id.main_SIM_seventeenIndex_middle),
                findViewById(R.id.main_SIM_eighteenIndex_middle),
                findViewById(R.id.main_SIM_nineteenIndex_middle),
                findViewById(R.id.main_SIM_twentyIndex_middle),
                findViewById(R.id.main_SIM_twentystIndex_middle),
                findViewById(R.id.main_SIM_twentyndIndex_middle),
                findViewById(R.id.main_SIM_twentyrdIndex_middle),
                findViewById(R.id.main_SIM_ftwentyfourthIndex_middle),
                findViewById(R.id.main_SIM_twentyfifthIndex_middle),
                findViewById(R.id.main_SIM_twentysixthIndex_middle),
                findViewById(R.id.main_SIM_twentyseventhIndex_middle),
                findViewById(R.id.main_SIM_twentyeighthIndex_middle),
                findViewById(R.id.main_SIM_twentyninthIndex_middle),
                findViewById(R.id.main_SIM_thirtyIndex_middle),
                findViewById(R.id.main_SIM_thirtystIndex_middle),
                findViewById(R.id.main_SIM_thirtyndIndex_middle),
                findViewById(R.id.main_SIM_thirtyrdIndex_middle),
                findViewById(R.id.main_SIM_thirtyfourthIndex_middle),
                findViewById(R.id.main_SIM_thirtyfifthIndex_middle),
                findViewById(R.id.main_SIM_thirtysixthIndex_middle),
                findViewById(R.id.main_SIM_thirtysevenIndex_middle),
                findViewById(R.id.main_SIM_thirtyeighthIndex_middle),
                findViewById(R.id.main_SIM_thirtyninethIndex_middle),
                findViewById(R.id.main_SIM_fourtyIndex_middle),
                findViewById(R.id.main_SIM_fourtystIndex_middle),
                findViewById(R.id.main_SIM_fourtyndIndex_middle),
                findViewById(R.id.main_SIM_fourtyrdIndex_middle),
                findViewById(R.id.main_SIM_fourtyfourthIndex_middle),
                findViewById(R.id.main_SIM_fourtyfifthIndex_middle),
                findViewById(R.id.main_SIM_fourtysixthIndex_middle),
                findViewById(R.id.main_SIM_fourtyseventhIndex_middle),
                findViewById(R.id.main_SIM_fourtyeighthIndex_middle),
                findViewById(R.id.main_SIM_fourtyninethIndex_middle),
                findViewById(R.id.main_SIM_fiftyIndex_middle),
                findViewById(R.id.main_SIM_fiftystIndex_middle),
                findViewById(R.id.main_SIM_fiftyndIndex_middle),
                findViewById(R.id.main_SIM_fiftyrdIndex_middle),
                findViewById(R.id.main_SIM_fiftyfourthIndex_middle),
                findViewById(R.id.main_SIM_fiftyfifthIndex_middle),
                findViewById(R.id.main_SIM_fiftysixthIndex_middle),
                findViewById(R.id.main_SIM_fiftyseventhIndex_middle),
                findViewById(R.id.main_SIM_fiftyeighthIndex_middle),
                findViewById(R.id.main_SIM_fiftyninethIndex_middle),
                findViewById(R.id.main_SIM_sixtyIndex_middle),
                findViewById(R.id.main_SIM_sixtystIndex_middle),
                findViewById(R.id.main_SIM_sixtyndIndex_middle),
                findViewById(R.id.main_SIM_sixtyrdIndex_middle),
                findViewById(R.id.main_SIM_sixtyfourthIndex_middle),
                findViewById(R.id.main_SIM_sixtyfifthIndex_middle),
                findViewById(R.id.main_SIM_sixtysixthIndex_middle),
                findViewById(R.id.main_SIM_sixtyseventhIndex_middle),
                findViewById(R.id.main_SIM_sixtyeighthIndex_middle),
                findViewById(R.id.main_SIM_sixtyninethIndex_middle),
                findViewById(R.id.main_SIM_seventyIndex_middle)

        };


        main_IMG_lives_middle = new ShapeableImageView[]{
                findViewById(R.id.main_SIM_stLife_middle),
                findViewById(R.id.main_SIM_ndLife_middle),
                findViewById(R.id.main_SIM_rdLife_middle),
                findViewById(R.id.main_SIM_fourthLife_middle),
                findViewById(R.id.main_SIM_fifthLife_middle)
        };
    }
}
