package com.mygame.theroadmusttaken.Data;

import com.mygame.theroadmusttaken.Exceptions.PossibleNumberOfColumnException;

public class Middle_Difficulty_Level_Builder extends Difficulty_Level_Builder{


    @Override
    public void buildSpeedRate() {
        difficultyLevel.setspeedRate(0.75);
    }

    @Override
    public void buildHeartNum() {
        difficultyLevel.setHeartNum(2);
    }

    @Override
    public void buildMatRows() {
        difficultyLevel.setMatRows(14);
    }

    @Override
    public void buildMatCols() {
        try {
            difficultyLevel.setMatCols(5);
        } catch (PossibleNumberOfColumnException e) {
            e.getMassage();
        }
    }

    @Override
    public void buildNUM_STEP_IN_PHASE() {
        difficultyLevel.setNUM_STEP_IN_PHASE(2);
    }
}
