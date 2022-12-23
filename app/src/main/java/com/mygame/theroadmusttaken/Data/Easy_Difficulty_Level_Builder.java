package com.mygame.theroadmusttaken.Data;


import com.mygame.theroadmusttaken.Exceptions.PossibleNumberOfColumnException;

public class Easy_Difficulty_Level_Builder extends Difficulty_Level_Builder{

    @Override
    public void buildSpeedRate() {
        difficultyLevel.setspeedRate(1.0);
    }

    @Override
    public void buildHeartNum() {
        difficultyLevel.setHeartNum(3);
    }

    @Override
    public void buildMatRows() {
    difficultyLevel.setMatRows(7);
    }

    @Override
    public void buildMatCols() {
        try {
            difficultyLevel.setMatCols(3);
        } catch (PossibleNumberOfColumnException e) {
            e.getMassage();
        }
    }

    @Override
    public void buildNUM_STEP_IN_PHASE() {
        difficultyLevel.setNUM_STEP_IN_PHASE(3);
    }
}
