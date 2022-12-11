package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;

@Data

public class CollectsPK implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer pid;

    private String uid;
}
