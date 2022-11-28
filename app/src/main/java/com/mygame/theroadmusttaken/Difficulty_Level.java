package com.mygame.theroadmusttaken;

public class Difficulty_Level {

    private double speedRate; // x - SEC per each phase
    private int heartNum;
    private int matRows;
    private int matCols;

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
