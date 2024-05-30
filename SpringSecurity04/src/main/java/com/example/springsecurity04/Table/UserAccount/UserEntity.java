package com.example.springsecurity04.Table.UserAccount;

import com.example.springsecurity04.Table.Common.CommonEntity;
import com.example.springsecurity04.Table.Post.PostEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String nickName;
    private String username;
    private String password;
    private String role;
    private String provider;
    private String email;
    private String userIp;
    private String imageUrl;

    @OneToOne(mappedBy = "userEntity" , cascade = CascadeType.ALL) //내 주인님
    @JsonIgnore
    private CommonEntity commonEntity;


    @Builder
    public UserEntity(String nickName, String username, String password, String role, String provider, String email,String imageUrl) {
        this.nickName = nickName;
        this.username = username;
        this.password = password;
        this.role = role;
        this.provider = provider;
        this.email = email;
        this.imageUrl = imageUrl;
    }
}
