package com.mygame.theroadmusttaken.Data;

import com.mygame.theroadmusttaken.R;

public class Heart extends Layout_Icons {

    public Heart(){
        setImageRes(R.drawable.ic_heart);
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public int getImageRes() {
        return imageRes;
    }

}
