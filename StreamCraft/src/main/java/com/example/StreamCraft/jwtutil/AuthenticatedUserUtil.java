package com.example.StreamCraft.jwtutil;


import com.example.StreamCraft.dto.user.UserInfoResponseDto;
import com.example.StreamCraft.service.userdetails.UserDetailsInformation;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticatedUserUtil {


    public static UserInfoResponseDto getUserAccount(){
        UserDetailsInformation principal = (UserDetailsInformation) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
      return principal.getInformation();
    }
}
