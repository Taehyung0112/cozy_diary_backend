package com.example.demo.service.impl;


import com.example.demo.dao.UserCategoryDao;
import com.example.demo.dto.CategoryResponse;
import com.example.demo.dto.UserCategoryRequest;
import com.example.demo.entity.UserCategory;
import com.example.demo.service.UserCategoryService;
import com.example.demo.vo.UserCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserCategoryServiceImpl implements UserCategoryService {
    @Autowired
    private UserCategoryDao userCategoryDao;

    @Override
    public List<CategoryResponse> getUserCategoryListByUid(String uid) {
        return userCategoryDao.findCategoryListByUid(uid);
    }

    @Override
    public Optional<String> addUserCategory(UserCategoryRequest userCategoryRequest) {
        try {
            for(int i = 0 ; i<userCategoryRequest.getUserCategory().size();i++){
                UserCategory userCategory = new UserCategory();
                userCategory.setCid(userCategoryRequest.getUserCategory().get(i).getCid());
                userCategory.setUid(userCategoryRequest.getUserCategory().get(i).getUid());
                userCategoryDao.save(userCategory);
            }

            return Optional.of("新增使用者喜好類別成功");
        } catch (Exception e){
            return Optional.of("新增使用者喜好類別時發生以下錯誤："+e.toString());
        }
    }

    @Override
    public Optional<String> deleteUserCategory(String uid) {
        try {
            userCategoryDao.deleteAllByUid(uid);
            return Optional.of("刪除使用者喜好類別成功");
        }catch (Exception e){
            return Optional.of("刪除使用者喜好類別時發生以下錯誤："+e.toString());
        }
    }
}
