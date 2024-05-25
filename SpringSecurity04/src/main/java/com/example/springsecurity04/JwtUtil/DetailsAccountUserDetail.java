package com.example.springsecurity04.JwtUtil;


import com.example.springsecurity04.Dto.UserInformation;
import com.example.springsecurity04.Service.jwtCheckService.UserDetailsInformation;
import org.springframework.security.core.context.SecurityContextHolder;

public class DetailsAccountUserDetail {


    public static UserInformation getUserAccount(){
        UserDetailsInformation principal = (UserDetailsInformation) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
      return principal.getInformation();
    }
}
