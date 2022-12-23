package com.mygame.theroadmusttaken;

import java.util.ArrayList;

public class DataManager {

    enum Car_Direction {
        RIGHT_DIRECTION,
        LEFT_DIRECTION
    }

    private Game_Layout gameLayout;
    private ArrayList<Cell> roadsLayoutMatrix;

    public DataManager (Difficulty_Level_Builder difficultyLevelBuilder){
        gameLayout = new Game_Layout();
        roadsLayoutMatrix= new ArrayList<>();
        gameLayout.setDifficultyLevelBuilder(difficultyLevelBuilder);
        gameLayout.constructDifficultyLevel();
        initMatrix(difficultyLevelBuilder.getDifficultyLevel().getMatCols(),
                difficultyLevelBuilder.getDifficultyLevel().getMatRows());

    }

    public void initMatrix(int numberCols, int numberRows) {
        for(int i = 0; i < numberCols * numberRows; i++){
            roadsLayoutMatrix.add(new Cell()
                    .setCar(null)
                    /*.setCoin(null)*/
                    .setRock(null));
        }

        //Car default/start position
        roadsLayoutMatrix.get(getGameLayout().getIndexCarStartPos()).setCar(new Car());
    }

    public Game_Layout getGameLayout() {
        return gameLayout;
    }

    public ArrayList<Cell> getRoadsLayoutMatrix() {
        return roadsLayoutMatrix;
    }

    public void setCarIndex(int indexOfCar, Car_Direction turn){
        getRoadsLayoutMatrix().get(indexOfCar).setCar(null);
        if(turn == Car_Direction.LEFT_DIRECTION){
            getRoadsLayoutMatrix().get(indexOfCar-1).setCar(new Car());
        }
        else if(turn == Car_Direction.RIGHT_DIRECTION){
            getRoadsLayoutMatrix().get(indexOfCar+1).setCar(new Car());
        }
    }
    public int getIndexOfRacingCar(){

        for(int i= 0; i < getGameLayout().getDifficultyLevelBuilder().getMatCols()/2;i++){
            if(roadsLayoutMatrix.get(getGameLayout().getIndexCarStartPos()+i+1).getCar() != null)
                return getGameLayout().getIndexCarStartPos()+i+1;
            else if(roadsLayoutMatrix.get(getGameLayout().getIndexCarStartPos()-i-1).getCar() != null)
                return getGameLayout().getIndexCarStartPos()-i-1;
        }
        if(roadsLayoutMatrix.get(getGameLayout().getIndexCarStartPos()).getCar()!= null)
            return getGameLayout().getIndexCarStartPos();
        return -1;
    }

    private void setRockIndex(int indexOfRock){
        getRoadsLayoutMatrix().get(indexOfRock).setRock(null);
        if(indexOfRock+getGameLayout().getDifficultyLevelBuilder().getMatCols() <
                getGameLayout().getDifficultyLevelBuilder().getMatCols() *
                        getGameLayout().getDifficultyLevelBuilder().getMatRows())
            getRoadsLayoutMatrix().get(indexOfRock+getGameLayout().getDifficultyLevelBuilder().getMatCols()).setRock(new Rock());
    }

    private void setCoinIndex(int indexOfCoin){
        getRoadsLayoutMatrix().get(indexOfCoin).setCoin(null);
        if(indexOfCoin+getGameLayout().getDifficultyLevelBuilder().getMatCols() <
                getGameLayout().getDifficultyLevelBuilder().getMatCols() *
                        getGameLayout().getDifficultyLevelBuilder().getMatRows())
            getRoadsLayoutMatrix().get(indexOfCoin+getGameLayout().getDifficultyLevelBuilder().getMatCols()).setCoin(new Coin());
    }


}
