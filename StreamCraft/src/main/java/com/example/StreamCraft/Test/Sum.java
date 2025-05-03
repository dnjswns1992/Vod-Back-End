package com.example.StreamCraft.Test;

public class Sum implements Calculator{

    private String msg = "안뇽하세요?";

    @Override
    public String getSum() {
        return this.msg;
    }
}
