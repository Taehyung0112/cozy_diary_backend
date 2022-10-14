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
public class TrackerResponse {
    private String tracker1;
    private String tracker2;
    private String name;
    private LocalDateTime trackTime;
    private String pic;

    public TrackerResponse(String tracker1,String tracker2,String name,LocalDateTime trackTime, String pic){
        this.tracker1 = tracker1;
        this.tracker2 = tracker2;
        this.name = name;
        this.trackTime = trackTime;
        this.pic = pic;
    }

}
