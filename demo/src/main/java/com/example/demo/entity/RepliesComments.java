package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity
@Table(name = "replies_comment")
public class RepliesComments implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rid;

    @Column(name = "text")
    private String text;

    @Column(name = "uid")
    private String uid;

    @Column(name = "replies_time", nullable = true)
    private String repliesTime;

    @Column(name = "likes")
    private Integer likes;

    @Column(name = "comment_id")
    private Integer commentId;

    @Formula("(select u.name from user u where u.google_id = uid)")
    private String username;

    @Formula("(select u.pic_resize from user u where u.google_id = uid)")
    private String pic;
}
