package com.example.demo.dto;

import com.example.demo.entity.UserCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserCategoryRequest {
    private List<UserCategory> userCategory;
}
