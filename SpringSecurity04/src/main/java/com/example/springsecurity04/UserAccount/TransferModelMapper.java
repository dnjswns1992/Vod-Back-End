package com.example.springsecurity04.UserAccount;

import com.example.springsecurity04.Table.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

@RequiredArgsConstructor
public class TransferModelMapper<T,R>{






    public  R getTransfer(T sourceObject , Class<R> destinationClass){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(sourceObject,destinationClass);
    }
   }

