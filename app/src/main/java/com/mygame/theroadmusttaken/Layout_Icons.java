package com.mygame.theroadmusttaken;

public abstract class Layout_Icons implements Visable{
    protected int imageRes;
    protected boolean visable;

    public abstract void setImageRes(int imageRes);
    public abstract void setVisable(boolean visable);
    public abstract int getImageRes();
}
