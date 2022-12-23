package com.mygame.theroadmusttaken.Data;

import com.mygame.theroadmusttaken.R;

public class Rock extends Layout_Icons{

    public Rock(){
        setImageRes(R.drawable.ic_rock);
    }
    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public int getImageRes() {
        return imageRes;
    }
}
