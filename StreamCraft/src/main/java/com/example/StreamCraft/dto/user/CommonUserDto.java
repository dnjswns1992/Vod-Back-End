package com.example.StreamCraft.dto.user;


import lombok.Data;

import java.time.LocalDate;

@Data
public class CommonUserDto {

    private String email;
    private String username;
    private String provider;
    private String nickname;
    private LocalDate createAt;
    private String realName;
    private String role;
}
