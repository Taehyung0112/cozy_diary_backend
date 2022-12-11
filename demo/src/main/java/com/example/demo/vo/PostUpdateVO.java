package com.example.demo.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostUpdateVO {
    private Integer pid;
    private String title;
    private String content;
}
