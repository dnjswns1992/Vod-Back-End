package com.example.StreamCraft.Entity.user;

import com.example.StreamCraft.Entity.commom.MergedUserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    private MergedUserEntity mergedUserEntity;


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
