package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@IdClass(TrackerPk.class)
@Table(name = "tracker")
public class Tracker implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tid;

    @Id
    @Column(name = "tracker1")
    private String tracker1;

    @Id
    @Column(name = "tracker2")
    private String tracker2;

    @Column(name = "track_time")
    private LocalDateTime trackTime;
}
