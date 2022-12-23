package com.mygame.theroadmusttaken.Data;

import com.mygame.theroadmusttaken.Data.Difficulty_Level;
import com.mygame.theroadmusttaken.Data.Difficulty_Level_Builder;

public class Game_Layout {

    private Difficulty_Level_Builder difficultyLevelBuilder;

    public void setDifficultyLevelBuilder(Difficulty_Level_Builder difficultyLevelBuilder) {
        this.difficultyLevelBuilder = difficultyLevelBuilder;
    }

    public Difficulty_Level getDifficultyLevelBuilder() {
        return difficultyLevelBuilder.getDifficultyLevel();
    }

    public void constructDifficultyLevel(){
        difficultyLevelBuilder.createNewDifficultyLevel();
        difficultyLevelBuilder.buildHeartNum();
        difficultyLevelBuilder.buildSpeedRate();
        difficultyLevelBuilder.buildMatCols();
        difficultyLevelBuilder.buildMatRows();
        difficultyLevelBuilder.buildNUM_STEP_IN_PHASE();
    }
    public int getIndexCarStartPos()
    {
        return getDifficultyLevelBuilder().getMatCols()
                * (getDifficultyLevelBuilder().getMatRows() - 2) +
                (getDifficultyLevelBuilder().getMatCols() / 2);
    }
}
