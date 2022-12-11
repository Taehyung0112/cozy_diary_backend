package com.example.demo.service;

import com.example.demo.dto.CategoryResponse;
import com.example.demo.dto.UserCategoryRequest;
import com.example.demo.entity.UserCategory;
import com.example.demo.vo.UserCategoryVO;

import java.util.List;
import java.util.Optional;

public interface UserCategoryService {

    public List<CategoryResponse> getUserCategoryListByUid(String uid);

    public Optional<String> addUserCategory(UserCategoryRequest userCategoryRequest);

    public Optional<String> deleteUserCategory(String uid);
}
