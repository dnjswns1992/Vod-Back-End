package com.example.springsecurity04.Test;

public class Sum implements Calculator{

    private String msg = "안뇽하세요?";

    @Override
    public String getSum() {
        return this.msg;
    }
}
