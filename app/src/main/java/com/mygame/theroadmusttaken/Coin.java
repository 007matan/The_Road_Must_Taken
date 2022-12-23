package com.mygame.theroadmusttaken;

public class Coin extends Layout_Icons{

    public Coin(){
    setImageRes(R.drawable.ic_dogecoin);
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }


    public int getImageRes() {
        return imageRes;
    }
}
