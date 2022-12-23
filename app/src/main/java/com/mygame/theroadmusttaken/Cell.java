package com.mygame.theroadmusttaken;

public class Cell {

    private Coin coin;
    private Rock rock;
    private Car car;

    public Cell(){

    }

    public Cell setCoin(Coin coin) {
        this.coin = coin;
        return this;
    }

    public Cell setRock(Rock rock) {
        this.rock = rock;
        return this;
    }

    public Cell setCar(Car car) {
        this.car = car;
        return  this;
    }

    public Rock getRock() {
        return rock;
    }

    public Car getCar() {
        return car;
    }

    public Coin getCoin() {
        return coin;
    }

}
