package com.example.StreamCraft.Entity.user;


import com.example.StreamCraft.Entity.commom.MergedUserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Oauth2Entity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int oauth2Id;
    private String email;
    private String nickName;
    private String provider;
    private String role;
    @CreationTimestamp
    private LocalDate createAt;
    private String userIp;
    private String imageUrl;

    @OneToOne(mappedBy ="oauth2Entity",cascade = CascadeType.ALL)
    @JsonIgnore
    private MergedUserEntity mergedUserEntity;


    //폼 로그인의 경우 username -> 입력 아이디
    //오어스의 경우 username -> 실제 이름

    //username -> 오어스 -> 이메일 , 폼 로그인 -> 입력 아이디

    @Builder
    public Oauth2Entity(String email, String nickName,String provider,String role,String Image) {

        this.email = email;
        this.nickName = nickName;
        this.provider = provider;
        this.role = role;
        this.imageUrl = Image;
    }
}
