package com.example.demo.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Where;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Indexed
@Getter
@Setter
@ToString
@Entity
@Table(name = "post")
@EqualsAndHashCode
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pid;

    @Column(name = "uid")
    private String uid;

    @FullTextField()
    @NaturalId(mutable = true)
    @Column(name = "title")
    private String title;

    @FullTextField()
    @Column(name = "content",nullable = true)
    private String content;

    @Column(name = "likes")
    private Integer likes;

    @Column(name = "collects")
    private Integer collects;

    @Column(name = "post_time")
    private String post_time;

    @Column(name = "modify_time",nullable = true)
    private String modify_time;

    @Column(name = "cover")
    private String cover;

    @Column(name = "cid" )
    private Integer cid;

    @Column(name = "post_lng", nullable = true)
    private double postLng;

    @Column(name = "post_lat" , nullable = true)
    private double postLat;


    @OneToMany(targetEntity = PostFile.class , cascade = CascadeType.ALL)
    @JoinColumn(name = "pid" , referencedColumnName = "pid")
    private List<PostFile> postFiles;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pid")
    private List<Comments> comments;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pid")
    @Where(clause = " type = 0")
    private List<Likes> likeList;
}
