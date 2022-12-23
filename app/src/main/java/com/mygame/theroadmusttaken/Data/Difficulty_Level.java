package com.mygame.theroadmusttaken.Data;

import com.mygame.theroadmusttaken.Exceptions.PossibleNumberOfColumnException;

public class Difficulty_Level {

    private double speedRate; // x - SEC per each phase
    private int heartNum;
    private int matRows;
    private int matCols;

    private int NUM_STEP_IN_PHASE;

    public int getNUM_STEP_IN_PHASE() {
        return NUM_STEP_IN_PHASE;
    }

    public void setNUM_STEP_IN_PHASE(int NUM_STEP_IN_PHASE) {
        this.NUM_STEP_IN_PHASE = NUM_STEP_IN_PHASE;
    }

    public void setspeedRate(double speedRate) {
        this.speedRate = speedRate;
    }

    public void setHeartNum(int heartNum) {
        this.heartNum = heartNum;
    }

    public void setMatRows(int matRows) {
        this.matRows = matRows;
    }

    public double getSpeedRate() {
        return speedRate;
    }

    public int getHeartNum() {
        return heartNum;
    }

    public int getMatRows() {
        return matRows;
    }

    public int getMatCols() {
        return matCols;
    }

    public void setMatCols(int matCols) throws PossibleNumberOfColumnException {
        if(matCols % 2 != 0)
            this.matCols = matCols;
        else
            throw new PossibleNumberOfColumnException();
    }

}
