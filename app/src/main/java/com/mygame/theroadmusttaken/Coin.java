package com.mygame.theroadmusttaken;

public class Coin extends Layout_Icons{

    public Coin(){
    setImageRes(R.drawable.ic_dogecoin);
    setVisable(false);
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public void setVisable(boolean visable) {
        this.visable = visable;
    }

    public int getImageRes() {
        return imageRes;
    }

    @Override
    public boolean isVisable() {
        return false;
    }
}
