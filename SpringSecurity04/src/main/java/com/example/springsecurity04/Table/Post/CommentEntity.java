package com.example.springsecurity04.Table.Post;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jdk.jfr.Timestamp;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class CommentEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;
    private String username;
    private String nickname;
    private String title;
    private String content;
    @CreationTimestamp
    private LocalDate createAt;

    @ManyToOne
    @JoinColumn(name = "postId")
    @JsonIgnore
    private PostEntity postEntity;


}
