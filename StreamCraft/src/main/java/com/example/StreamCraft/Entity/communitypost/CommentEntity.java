package com.example.StreamCraft.Entity.communitypost;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

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
