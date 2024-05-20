package com.example.springsecurity04.Test;

public class Minus implements Calculator{

    private String msg = "this is my";
    @Override
    public String getSum() {
        return msg;
    }
}
