package com.mygame.theroadmusttaken;

abstract class Difficulty_Level_Builder {

    protected Difficulty_Level difficultyLevel;

    public Difficulty_Level getDifficultyLevel() {
        return difficultyLevel;
    }

    public void createNewDifficultyLevel() {
        difficultyLevel = new Difficulty_Level();
    }

    public abstract void buildSpeedRate();
    public abstract void buildHeartNum();
    public abstract void buildMatRows();
    public abstract void buildMatCols();
    public abstract void buildNUM_STEP_IN_PHASE();
}
