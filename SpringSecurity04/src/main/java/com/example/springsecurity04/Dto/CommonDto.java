package com.example.springsecurity04.Dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class CommonDto {

    private String email;
    private String username;
    private String provider;
    private String nickname;
    private LocalDate createAt;
    private String realName;
    private String role;
}
