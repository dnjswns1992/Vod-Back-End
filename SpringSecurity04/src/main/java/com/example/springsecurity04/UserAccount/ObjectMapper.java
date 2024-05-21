package com.example.springsecurity04.UserAccount;

import org.modelmapper.ModelMapper;

public class ObjectMapper <T,R>{

    public R getMapper(T souceObject , Class<R> endMapper ) {

        ModelMapper mapper = new ModelMapper();
       return mapper.map(souceObject,endMapper);
    }
}
