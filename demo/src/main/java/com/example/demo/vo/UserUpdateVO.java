package com.example.demo.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserUpdateVO {
    private String google_id;
    private String name;
    private String birth;
    private Integer sex;
    private String introduction;
}
