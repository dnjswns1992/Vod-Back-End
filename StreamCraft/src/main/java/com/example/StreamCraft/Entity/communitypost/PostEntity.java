package com.example.StreamCraft.Entity.communitypost;


import com.example.StreamCraft.Entity.commom.MergedUserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Setter
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;
    private String username;
    private String title;
    private String content;
    private String nickname;
    @CreationTimestamp
    private LocalDate createTime;
    private int commentCount;
    private int reportCount;
    @Lob
    private byte[] user_Profile;
    private int postHit;

    @OneToMany(mappedBy = "postEntity",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CommentEntity> commentEntityList = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "commonId")
    @JsonIgnore
    private MergedUserEntity mergedUserEntity;




}
