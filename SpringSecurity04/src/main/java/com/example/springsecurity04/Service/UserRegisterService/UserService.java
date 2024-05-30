package com.example.springsecurity04.Service.UserRegisterService;


import com.example.springsecurity04.Dto.UserDto;
import com.example.springsecurity04.Converter.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserConverter converter;
    public boolean join(UserDto dto, HttpServletRequest servletRequest, MultipartFile multipartFile){

         return converter.FormLoginUserRegister(dto,servletRequest,multipartFile);

    }
}
