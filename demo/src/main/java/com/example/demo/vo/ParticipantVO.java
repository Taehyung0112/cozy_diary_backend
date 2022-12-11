package com.example.demo.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ParticipantVO {
    public Integer partId;
    public String participant;
    public String reason;
    public Integer qualified;
    public Integer aid;
    public String releaseTime;
}
