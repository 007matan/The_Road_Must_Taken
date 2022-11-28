package com.mygame.theroadmusttaken;

import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class GameManager {



    protected static final int MAX_NUM_LIFES = 3;
    private static final int ROW_TWO_UNSEEN = 2;
    private DataManager dataManager;
    //private int indexOfCar; // Moving Right & Left
    private ArrayList<Integer> indexesOfRocksArr = new ArrayList<>(); // Moving Down
    //private ArrayList<Integer> indexesOfCoins = new ArrayList<>(); // Moving Down
    private int lifes;
    //private int score;

/*
    public void getIndexOfCar()
    {}

 */

    public GameManager() {
        dataManager = new DataManager();
        initRoadGame();
    }

    private void initRoadGame() {
        //happened only once
        setLifes();
        //setScore();
        // * set first rock locations (index between)
        setRocksLocations(ROW_TWO_UNSEEN);
    }
    /*
    private void setScore() {
        this.score = 0;
    }
*/

    public ArrayList<Integer> getIndexesOfRocksArr() {
        return indexesOfRocksArr;
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

    private void setRocksLocations(int level) {
        int lowLimit = dataManager.getGameLayout().getDifficultyLevelBuilder().getMatCols();
        int highLimit = dataManager.getGameLayout().getDifficultyLevelBuilder().getMatCols() * level;
        boolean isAvilableEmptyPlace = false;
        int randomIndexForNewRock = new Random().nextInt((highLimit - lowLimit) + 1) + lowLimit;
        randomIndexForNewRock *= -1;
        //if() there are already coin or rock
        for(int i = 0; i < indexesOfRocksArr.size(); i++){
            if(randomIndexForNewRock == indexesOfRocksArr.get(i)) /*|| randomIndexForNewRock == indexesOfRocksArr.get(i)*/
                isAvilableEmptyPlace = true;
        }
        if(!isAvilableEmptyPlace)
            indexesOfRocksArr.add(randomIndexForNewRock);
    }
    /*
    private void setCoinsLocations(int level) {
        int lowLimit = dataManager.getGameLayout().getDifficultyLevelBuilder().getMatCols();
        int highLimit = dataManager.getGameLayout().getDifficultyLevelBuilder().getMatCols() * level;
        boolean isAvilableEmptyPlace = false;
        int randomIndexForNewRock = new Random().nextInt((highLimit - lowLimit) + 1) + lowLimit;
        randomIndexForNewRock *= -1;

        //if() there are already coin or rock
        for(int i = 0; i <= indexesOfRocksArr.size(); i++){
            if(randomIndexForNewRock == indexesOfRocksArr.get(i) || randomIndexForNewRock == indexesOfRocksArr.get(i))
                isAvilableEmptyPlace = true;
        }
        if(!isAvilableEmptyPlace)
                indexesOfRocksArr.add(randomIndexForNewRock);
        }
    }
     */

    public void setLifes() {
        this.lifes = dataManager.getGameLayout().getDifficultyLevelBuilder().getHeartNum();
    }

    public void deleteExpiresRockFromArray(){
        for(int i = 0; i < indexesOfRocksArr.size(); i++){
           if(indexesOfRocksArr.get(i) > dataManager.getGameLayout().getDifficultyLevelBuilder().getMatRows()
                                    * dataManager.getGameLayout().getDifficultyLevelBuilder().getMatCols()
                                    - dataManager.getGameLayout().getDifficultyLevelBuilder().getMatCols() - 1){
               indexesOfRocksArr.remove(i);
           }
        }
    }

    /*
    public void deleteExpiresCoinsFromArray(){
        int i = 0;
        while(!indexesOfCoins.isEmpty()){
            if(indexesOfCoins.get(i) > dataManager.getGameLayout().getDifficultyLevelBuilder().getMatRows()
                    * dataManager.getGameLayout().getDifficultyLevelBuilder().getMatCols()
                    - dataManager.getGameLayout().getDifficultyLevelBuilder().getMatCols() - 1){
                indexesOfCoins.remove(i);
            }
            i++;
        }
    }
     */

    public boolean mainFunction(){
        boolean heat = false;
        layoutElementsDown();
        if(checkRockClash()) {
           if(updateLifes(-1) == 0)
               Log.w("Game_Over", "Crush!!! Game Over");
           heat = true;
        }
        /*
        if(checkCoinClash()) {
            earnCoin();
        }
         */

       deleteExpiresRockFromArray();
       //deleteExpiresCoinsFromArray();


        //each difficult have diffrent accessable places to drop a rock
        int unSeenRowsLevelToStartDroppinRock = new Random().nextInt((
                getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatCols()- 1) + 1) + 1;
        setRocksLocations(unSeenRowsLevelToStartDroppinRock);

        /*
        int unSeenRowsLevelToStartDroppinCoin = new Random().nextInt((
                getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatCols() * 2- 1) + 1) + 1;
        setCoinsLocations(unSeenRowsLevelToStartDroppinCoin);

         */
        return heat;
    }
    /*
    private void earnCoin()
    {
        this.score++;
    }
*/
    private void layoutElementsDown() {
        rocksDown();
        //coinsDown();
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
                        /* .setCoin(null)*/
                        .setRock(null);
            }
        }
        for(int rocksMatIndex = 0; rocksMatIndex < indexesOfRocksArr.size();rocksMatIndex++){
            if(indexesOfRocksArr.get(rocksMatIndex) > 0) {
                getDataManager().getRoadsLayoutMatrix().get(indexesOfRocksArr.get(rocksMatIndex)).setRock(new Rock());
                // and up here
            }
        }
        /*
        while(!indexesOfCoinsArr.isEmpty()){
            if(indexesOfRocksArr.get(rocksMatIndex) > 0){
                getDataManager().getRoadsLayoutMatrix().get(indexesOfCoinsArr.get(coinsMatIndex)).setCoin(new Coin());
                coinsMatIndex++;
            }
        }
         */
    }

//    private void coinsDown() {
//        int newVal;
//        for(int i = 0; i < indexesOfCoinsArr.size(); i++){
//            newVal = indexesOfCoins.get(i) + getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatRows();
//            indexesOfCoins.set(i, newVal);
//        }
//    }

    private void rocksDown() {
        int newVal;
        for(int i = 0; i < indexesOfRocksArr.size(); i++){
            newVal = indexesOfRocksArr.get(i) + getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatCols();
            indexesOfRocksArr.set(i, newVal);
        }
    }

    private boolean checkRockClash() {
        /*
        int i = 0;
        while(!indexesOfRocksArr.isEmpty() && (indexesOfRocksArr.get(i) >
                dataManager.getGameLayout().getDifficultyLevelBuilder().getMatCols()
                * dataManager.getGameLayout().getDifficultyLevelBuilder().getMatRows()
                - dataManager.getGameLayout().getDifficultyLevelBuilder().getMatCols() - 1)){
            if(indexesOfRocksArr.get(i) == getDataManager().getIndexOfRacingCar())
                return true;
            i++;
        }
        return false;
        */
        for(int i = 0; i < indexesOfRocksArr.size() ; i++){
            if(indexesOfRocksArr.get(i) >
                    dataManager.getGameLayout().getDifficultyLevelBuilder().getMatCols()
                            * dataManager.getGameLayout().getDifficultyLevelBuilder().getMatRows()
                            - (dataManager.getGameLayout().getDifficultyLevelBuilder().getMatCols()*2) - 1){
                if(indexesOfRocksArr.get(i) == getDataManager().getIndexOfRacingCar()){
                    return true;
                }
            }
        }
        return false;
    }



//    private boolean checkCoinClash() {
//        int i = 0;
//        while(!indexesOfCoins.isEmpty() && (indexesOfRocks.get(i) >
//                dataManager.getGameLayout().getDifficultyLevelBuilder().getMatCols()
//                        * dataManager.getGameLayout().getDifficultyLevelBuilder().getMatRows()
//                        - dataManager.getGameLayout().getDifficultyLevelBuilder().getMatCols() - 1)){
//            if(indexesOfCoins.get(i) == getIndexOfCar())
//                return true;
//            i++;
//        }
//        return false;
//    }

    public boolean moveCar(DataManager.Car_Direction turn_direction){
        //Check if legal/valid
        boolean heat = false;
        if(isValidTurn(turn_direction))
        {
            changeCarLocation(turn_direction);
            if(checkRockClash()) {
                if (updateLifes(-1) == 0) {
                    Log.w("Game_Over", "Crush!!! Game Over");

                }
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
        int indexofWishTurn = 0;
        if(turn_direction == DataManager.Car_Direction.LEFT_DIRECTION){
            if(indexCurrLocation % getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatCols() == 0)
                return false;
            else
                return true;
                //indexofWishTurn = indexCurrLocation - 1 ;
        }
        else if(turn_direction == DataManager.Car_Direction.RIGHT_DIRECTION){
            if(indexofWishTurn % getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatCols() ==
                    getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatCols() - 1)
                return false;
            else
                return true;
        }

/*
        if((indexofWishTurn % getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatCols() == 0)
        || indexofWishTurn % getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatCols() ==
                getDataManager().getGameLayout().getDifficultyLevelBuilder().getMatCols() - 1){
            return false;
        }

 */
        return false;

    }



}
