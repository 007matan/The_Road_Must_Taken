package com.mygame.theroadmusttaken.Data;

import com.mygame.theroadmusttaken.R;

public class Car extends Layout_Icons{

    public Car(){
        setImageRes(R.drawable.ic_racing_car);
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }


    public int getImageRes() {
        return imageRes;
    }
}
