package com.example.StreamCraft.UserAccount;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

@RequiredArgsConstructor
public class TransferModelMapper<T,R>{






    public  R getTransfer(T sourceObject , Class<R> destinationClass){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(sourceObject,destinationClass);
    }
   }

