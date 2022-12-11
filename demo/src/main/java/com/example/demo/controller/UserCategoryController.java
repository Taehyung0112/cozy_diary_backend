package com.example.demo.controller;


import com.example.demo.dto.CategoryResponse;
import com.example.demo.dto.UserCategoryRequest;
import com.example.demo.entity.UserCategory;
import com.example.demo.service.UserCategoryService;
import com.example.demo.util.JsonResponse;
import com.example.demo.vo.UserCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class UserCategoryController {
    @Autowired
    private UserCategoryService userCategoryService;

    @RequestMapping(value = "/addUserCategory", method = RequestMethod.POST)
    public ResponseEntity<?> addUserCategory(@RequestBody UserCategoryRequest userCategoryRequest){
        try{
            Optional<String> optional = userCategoryService.addUserCategory(userCategoryRequest);
            return JsonResponse.generateResponse(optional.get(), HttpStatus.OK, "");
        }catch (Exception e){
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @RequestMapping(value = "/userCategoryList", method = RequestMethod.GET)
    public ResponseEntity<Object> getUserCategoryList(@RequestParam String uid){
        try{
            List<CategoryResponse> userCategoryList = userCategoryService.getUserCategoryListByUid(uid);
            return JsonResponse.generateResponse("查詢使用者喜好類別成功",HttpStatus.OK,userCategoryList);
        }catch (Exception e){
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @RequestMapping(value = "/deleteUserCategory", method = RequestMethod.POST)
    public ResponseEntity<Object> deleteUserCategory(@RequestParam String uid){
        try{
            Optional <String> userCategoryList = userCategoryService.deleteUserCategory(uid);
            return JsonResponse.generateResponse("刪除使用者喜好類別成功",HttpStatus.OK,userCategoryList);
        }catch (Exception e){
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
