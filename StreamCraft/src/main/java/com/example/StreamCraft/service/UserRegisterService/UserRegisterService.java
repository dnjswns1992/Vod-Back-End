package com.example.StreamCraft.service.UserRegisterService;


import com.example.StreamCraft.dto.user.UserRegisterDto;
import com.example.StreamCraft.converter.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class UserRegisterService {
    private final UserConverter converter;
    public boolean join(UserRegisterDto dto, HttpServletRequest servletRequest, MultipartFile multipartFile){

         return converter.FormLoginUserRegister(dto,servletRequest,multipartFile);

    }
}
