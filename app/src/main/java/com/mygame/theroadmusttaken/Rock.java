package com.mygame.theroadmusttaken;

public class Rock extends Layout_Icons{

    public Rock(){
        setImageRes(R.drawable.ic_rock);
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
