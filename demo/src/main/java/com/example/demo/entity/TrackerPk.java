package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class TrackerPk implements Serializable {
    private static final long serialVersionUID = 1L;

    private String tracker1;

    private String tracker2;
}
