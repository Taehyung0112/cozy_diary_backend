package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@IdClass(CollectsPK.class)
@Table(name = "collects")
public class Collects implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "pid")
    private Integer pid;

    @Id
    @Column(name = "uid")
    private String uid;

    @Column(name = "collect_time")
    private String collectTime;

}
