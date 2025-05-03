package com.example.StreamCraft.Entity.commom;

import com.example.StreamCraft.Entity.user.Oauth2Entity;
import com.example.StreamCraft.Entity.communitypost.PostEntity;
import com.example.StreamCraft.Entity.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class MergedUserEntity {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commonId;
    private String username;
    private String provider;
    private String nickname;
    private String role;




    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "userId")
    private UserEntity userEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "oauth2Id")
     private  Oauth2Entity oauth2Entity;


    @OneToMany(mappedBy = "mergedUserEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PostEntity> postEntities = new ArrayList<>();




    public MergedUserEntity(UserEntity userEntity) {
        this.nickname = userEntity.getNickName();
        this.role = userEntity.getRole();
        this.provider = userEntity.getProvider();
        this.userEntity = userEntity;
        this.username = userEntity.getUsername();
    }
    @Builder
    public MergedUserEntity(String username, String provider, String nickname, String role, Oauth2Entity oauth2Entity) {
        this.username = username;
        this.provider = provider;
        this.nickname = nickname;
        this.role = role;
        this.oauth2Entity = oauth2Entity;
    }

    public MergedUserEntity() {

    }
}
