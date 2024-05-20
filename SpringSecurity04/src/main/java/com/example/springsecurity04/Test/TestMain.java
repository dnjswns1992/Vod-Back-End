package com.example.springsecurity04.Test;

import java.util.ArrayList;
import java.util.List;

public class TestMain {


    public static void main(String[] args) {
        List<Calculator> list = new ArrayList<>();
        list.add(new Calculator() {
            @Override
            public String getSum() {
                return "Test success?";
            }
        });
        Calculator calculator = list.get(0);
        System.out.println(calculator.getSum());
    }
}
