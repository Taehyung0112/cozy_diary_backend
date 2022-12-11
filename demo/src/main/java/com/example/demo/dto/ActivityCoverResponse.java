package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ActivityCoverResponse {
    private Integer aid;
    private Integer likes;
    private String cover;
    private String activityName;
    private String pic;
    private String username;
    private LocalDateTime activityTime;
    private Double placeLng;
    private Double placeLat;

    public ActivityCoverResponse(Integer aid,String cover ,Integer likes, String activityName, String pic, String username,LocalDateTime activityTime, Double placeLng ,Double placeLat){
        this.aid = aid;
        this.likes = likes;
        this.cover = cover;
        this.activityName = activityName;
        this.pic = pic;
        this.username = username;
        this.activityTime = activityTime;
        this.placeLat = placeLat;
        this.placeLng = placeLng;
    }

}
