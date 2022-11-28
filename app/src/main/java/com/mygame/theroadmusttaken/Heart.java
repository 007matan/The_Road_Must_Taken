package com.mygame.theroadmusttaken;

public class Heart extends Layout_Icons {

    public Heart(){
        setImageRes(R.drawable.ic_heart);
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
