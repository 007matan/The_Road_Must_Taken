package com.mygame.theroadmusttaken.Exceptions;

public class PossibleNumberOfColumnException extends Throwable {
    protected String massage = "Invalid number of columns! \n Please insert odd number";

    public String getMassage() {
        return massage;
    }
}
