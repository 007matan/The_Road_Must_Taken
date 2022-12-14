package com.mygame.theroadmusttaken.Manegement;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.mygame.theroadmusttaken.Data.Coin;
import com.mygame.theroadmusttaken.Data.Difficulty_Level_Builder;
import com.mygame.theroadmusttaken.Data.Record;
import com.mygame.theroadmusttaken.Data.Rock;
import com.mygame.theroadmusttaken.Data.RecordList;
import com.mygame.theroadmusttaken.Protocol.CallBack_LocationsProtocol;
import com.mygame.theroadmusttaken.SharedPreferences.RecordSP;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;


public class GameManager {


    private CallBack_LocationsProtocol callBack_locationsProtocol;


    private DataManager dataManager;
    private ArrayList<Integer> indexesOfRocksArr;// Moving Down
    private ArrayList<Integer> indexesOfCoinsArr;// Moving Down
    private int lifes;
    private int score;
    private int distance;


    private RecordList recordArr;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public GameManager(Difficulty_Level_Builder difficultyLevelBuilder, CallBack_LocationsProtocol _callBack_locationsProtocol) {

        indexesOfRocksArr = new ArrayList<>();
        indexesOfCoinsArr = new ArrayList<>();
        recordArr = new RecordList();
        dataManager = new DataManager(difficultyLevelBuilder);
        initRoadGame();
        setRecordsArrFromSP();
        this.callBack_locationsProtocol = _callBack_locationsProtocol;
    }



    private void initRoadGame() {
        //happened only once
        setLifes();
        setScore();
        setDistance();
        // * set first rock locations (index between)
        setRocksLocations(getDataManager().getGameLayout().getDifficultyLevelBuilder().getNUM_STEP_IN_PHASE());
        setCoinsLocations(getDataManager().getGameLayout().getDifficultyLevelBuilder().getNUM_STEP_IN_PHASE());

    }

    private void setScore() {
        this.score = 0;
    }
    private void setDistance(){
        this.distance = 0;
    }

    public int getScore() {
        return score;
    }

    public int getDistance() {
        return distance;
    }

    public ArrayList<Integer> getIndexesOfRocksArr() {
        return indexesOfRocksArr;
    }

    public ArrayList<Integer> getIndexesOfCoinsArr() {
        return indexesOfCoinsArr;
    }

    public int getLifes() {
        return lifes;
    }



    public DataManager getDataManager() {
        return dataManager;
    }

    public int updateLifes(int lifes) {
        this.lifes += lifes;
        return this.lifes;
    }

    public void updateDistance(int meter) {
        this.distance += meter;
    }

    private void setRocksLocations(int level) {
        if((getDistance()+1) % level== 0) {
            int lowLimit = 0;
            int highLimit = dataManager.getGameLayout().getDifficultyLevelBuilder().getMatCols();
            boolean isAvilableEmptyPlaceForRock = false;
            int randomIndexForNewRock = new Random().nextInt((highLimit - lowLimit));
            randomIndexForNewRock *= -1;
            //if() there are already coin or rock
            for (int coinsidx = 0; coinsidx < indexesOfCoinsArr.size(); coinsidx++) {
                if (randomIndexForNewRock == indexesOfCoinsArr.get(coinsidx) /*|| randomIndexForNewCoin == indexesOfCoinsArr.get(i)*/)
                    isAvilableEmptyPlaceForRock = true;
            }
            for (int rocksIdx = 0; rocksIdx < indexesOfRocksArr.size(); rocksIdx++) {
                if (randomIndexForNewRock == indexesOfRocksArr.get(rocksIdx)) /*|| randomIndexForNewRock == indexesOfRocksArr.get(i)*/
                    isAvilableEmptyPlaceForRock = true;
            }
            if (!isAvilableEmptyPlaceForRock)
                indexesOfRocksArr.add(randomIndexForNewRock);
        }
    }

    private void setCoinsLocations(int level) {
        if((getDistance()+1) % level == 0) {
            int lowLimit = 0;
            int highLimit = dataManager.getGameLayout().getDifficultyLevelBuilder().getMatCols();
            boolean isAvilableEmptyPlaceForCoin = false;
            int randomIndexForNewCoin = new Random().nextInt((highLimit - lowLimit));
            randomIndexForNewCoin *= -1;

            //if() there are already coin or rock
            for (int rocksIdx = 0; rocksIdx < indexesOfRocksArr.size(); rocksIdx++) {
                if (randomIndexForNewCoin == indexesOfRocksArr.get(rocksIdx)) /*|| randomIndexForNewRock == indexesOfRocksArr.get(i)*/
                    isAvilableEmptyPlaceForCoin = true;
            }
            for (int coinsidx = 0; coinsidx < indexesOfCoinsArr.size(); coinsidx++) {
                if (randomIndexForNewCoin == indexesOfCoinsArr.get(coinsidx) /*|| randomIndexForNewCoin == indexesOfCoinsArr.get(i)*/)
                    isAvilableEmptyPlaceForCoin = true;
            }

            if (!isAvilableEmptyPlaceForCoin)
                indexesOfCoinsArr.add(randomIndexForNewCoin);
        }
    }


    public void setLifes() {
        this.lifes = dataManager.getGameLayout().getDifficultyLevelBuilder().getHeartNum();
    }

    public void deleteExpiresRockFromArray(){
        for(int dERidx = 0; dERidx < indexesOfRocksArr.size(); dERidx++){
           if(indexesOfRocksArr.get(dERidx) > dataManager.getGameLayout().getDifficultyLevelBuilder().getMatRows()
                                    * dataManager.getGameLayout().getDifficultyLevelBuilder().getMatCols()
                                    - dataManager.getGameLayout().getDifficultyLevelBuilder().getMatCols() - 1){
               indexesOfRocksArr.remove(dERidx);
           }
        }
    }


    public void deleteExpiresCoinsFromArray(){
        for(int dECidx = 0; dECidx < indexesOfCoinsArr.size(); dECidx++){
           if(indexesOfCoinsArr.get(dECidx) > dataManager.getGameLayout().getDifficultyLevelBuilder().getMatRows()
                                    * dataManager.getGameLayout().getDifficultyLevelBuilder().getMatCols()
                                    - dataManager.getGameLayout().getDifficultyLevelBuilder().getMatCols() - 1){
               indexesOfCoinsArr.remove(dECidx);
           }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean mainFunction(){
        boolean heat = false;
        layoutElementsDown();
        updateDistance(1);
        if(checkRockClash()) {
           if(updateLifes(-1) == 0) {
               Log.w("Game_Over", "Crush!!! Game Over");
               insertRecord();
           }
           heat = true;
        }

        if(checkCoinClash()) {
            earnCoin();
        }


       deleteExpiresRockFromArray();
       deleteExpiresCoinsFromArray();


        setCoinsLocations(getDataManager().getGameLayout().getDifficultyLevelBuilder().getNUM_STEP_IN_PHASE());

        //each difficult have diffrent accessable places to drop a rock

        setRocksLocations(getDataManager().getGameLayout().getDifficultyLevelBuilder().getNUM_STEP_IN_PHASE());





        return heat;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setRecordsArrFromSP() {
        RecordList recordList;
        String recordListSP = RecordSP.getInstance().getString("SP_KEY_RECORD_LIST", "Nun");
        if(!Objects.equals(recordListSP, "Nun")) {
            recordList = new Gson().fromJson(recordListSP, RecordList.class);
            this.recordArr = recordList;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void insertRecord() {
        LocalDate localDate = LocalDate.now();
        String localDateStr = localDate.toString();
        //Need to get gps location
        callBack_locationsProtocol.shortcut_configure_premission();
        double recLat = callBack_locationsProtocol.getLatFromActivity_Game();
        double recLog = callBack_locationsProtocol.getLogFromActivity_Game();

        int rPoints = this.distance+this.getScore()*2;
        Record record = new Record(localDateStr, recLat, recLog, rPoints);
        if(recordArr.getRecords().size() >= 10){
            this.recordArr.getRecords().add(record);
            Collections.sort(recordArr.getRecords());
            this.recordArr.getRecords().remove(0);
        }
        else{
            this.recordArr.getRecords().add(record);
        }

        String recordListJson = new Gson().toJson(recordArr);
        RecordSP.getInstance().putString("SP_KEY_RECORD_LIST", recordListJson);

    }

    private void earnCoin()
    {
        this.score++;
    }

    private void layoutElementsDown() {
        rocksDown();
        coinsDown();
        updateRoadsMatrix();
    }

    private void updateRoadsMatrix() {
        int roadsMatIndex;
        for(roadsMatIndex = 0; roadsMatIndex < getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatRows()
                                             + getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatCols();
        roadsMatIndex++)
        {
            if(roadsMatIndex != getDataManager().getIndexOfRacingCar())
            {
                //
                getDataManager().getRoadsLayoutMatrix().get(roadsMatIndex)
                        //setRockIndex - is function on data manger
                        //actually we are not allowed to do that up here
                         .setCoin(null)
                        .setRock(null);
            }
        }
        for(int rocksMatIndex = 0; rocksMatIndex < indexesOfRocksArr.size();rocksMatIndex++){
            if(indexesOfRocksArr.get(rocksMatIndex) > 0 &&
                    indexesOfRocksArr.get(rocksMatIndex) < getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatCols() *
                            getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatCols()) {
                getDataManager().getRoadsLayoutMatrix().get(indexesOfRocksArr.get(rocksMatIndex)).setRock(new Rock());
                // and up here
            }
        }
        for(int coinsMatIndex = 0; coinsMatIndex < indexesOfCoinsArr.size();coinsMatIndex++){
            if(indexesOfCoinsArr.get(coinsMatIndex) > 0 &&
                    indexesOfCoinsArr.get(coinsMatIndex) < getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatCols() *
                            getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatCols()) {
                getDataManager().getRoadsLayoutMatrix().get(indexesOfCoinsArr.get(coinsMatIndex)).setCoin(new Coin());
                // and up here
            }
        }
    }

    private void coinsDown() {
        int coinNewVal;
        for(int cDownidx = 0; cDownidx < indexesOfCoinsArr.size(); cDownidx++){
            coinNewVal = indexesOfCoinsArr.get(cDownidx) + getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatCols();
            indexesOfCoinsArr.set(cDownidx, coinNewVal);
        }
    }

    private void rocksDown() {
        int rockNewVal;
        for(int rDownidx = 0; rDownidx < indexesOfRocksArr.size(); rDownidx++){
            rockNewVal = indexesOfRocksArr.get(rDownidx) + getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatCols();
            indexesOfRocksArr.set(rDownidx, rockNewVal);
        }
    }

    private boolean checkRockClash() {
        for(int cRCidx = 0; cRCidx < indexesOfRocksArr.size() ; cRCidx++){
            if(indexesOfRocksArr.get(cRCidx) >
                    dataManager.getGameLayout().getDifficultyLevelBuilder().getMatCols()
                            * dataManager.getGameLayout().getDifficultyLevelBuilder().getMatRows()
                            - (dataManager.getGameLayout().getDifficultyLevelBuilder().getMatCols()*2) - 1){
                if(indexesOfRocksArr.get(cRCidx) == getDataManager().getIndexOfRacingCar()){
                    return true;
                }
            }
        }
        return false;
    }



    private boolean checkCoinClash() {
        for(int cCCidx = 0; cCCidx < indexesOfCoinsArr.size() ; cCCidx++){
            if(indexesOfCoinsArr.get(cCCidx) >
                    dataManager.getGameLayout().getDifficultyLevelBuilder().getMatCols()
                            * dataManager.getGameLayout().getDifficultyLevelBuilder().getMatRows()
                            - (dataManager.getGameLayout().getDifficultyLevelBuilder().getMatCols()*2) - 1){
                if(indexesOfCoinsArr.get(cCCidx) == getDataManager().getIndexOfRacingCar()){
                    return true;
                }
            }
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean moveCar(DataManager.Car_Direction turn_direction){
        //Check if legal/valid
        if(isValidTurn(turn_direction))
        {
            changeCarLocation(turn_direction);
            if(checkRockClash()) {
                if (updateLifes(-1) == 0) {
                    Log.w("Game_Over", "Crush!!! Game Over");
                    insertRecord();
                }
            }
            if(checkCoinClash()) {
                earnCoin();
            }
             return true;
            //hit but the driver can drive to rock, not out of bounds, so promitted move - thats why true

        }

        return false;
    }
    //row 53 DataManager
    private void changeCarLocation(DataManager.Car_Direction turn_direction) {
        getDataManager().setCarIndex(getDataManager().getIndexOfRacingCar(), turn_direction);
    }

    private boolean isValidTurn(DataManager.Car_Direction turn_direction) {
        int indexCurrLocation = getDataManager().getIndexOfRacingCar();
        if(turn_direction == DataManager.Car_Direction.LEFT_DIRECTION){
            if(indexCurrLocation % getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatCols() == 0)
                return false;
            else
                return true;
        }
        else if(turn_direction == DataManager.Car_Direction.RIGHT_DIRECTION){
            if(indexCurrLocation % getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatCols() ==
                    getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatCols() - 1) {
                return false;
            }
            else{
                return true;
                }
        }
        return false;

    }



}
