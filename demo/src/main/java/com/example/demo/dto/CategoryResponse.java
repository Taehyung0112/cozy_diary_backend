package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CategoryResponse {
    private String uid;
    private Integer cid;
    private String category;

    public CategoryResponse(String uid, Integer cid ,String category){
        this.uid = uid;
        this.cid = cid;
        this.category = category;
    }

}
