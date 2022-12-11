package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "comments")
public class Comments implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @Column(name = "text")
    private String text;

    @Column(name = "uid")
    private String uid;

    @Column(name = "comment_time")
    private String commentTime;

    @Column(name = "likes" , nullable = true)
    private Integer likes;

    @Column(name = "pid")
    private Integer pid;

    @Formula("(select u.name from user u where u.google_id = uid)")
    private String username;

    @Formula("(select u.pic_resize from user u where u.google_id = uid)")
    private String pic;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id")
    private List<RepliesComments> repliesComments;


}
