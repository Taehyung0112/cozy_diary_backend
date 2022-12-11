package com.example.demo.controller;


import com.example.demo.dto.LikesResponse;
import com.example.demo.entity.Likes;
import com.example.demo.service.LikesService;
import com.example.demo.util.JsonResponse;
import com.example.demo.vo.LikesVO;
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
public class LikesController {
    @Autowired
    private LikesService likesService;

    @RequestMapping(value = "/postLikesList", method = RequestMethod.GET)
    public ResponseEntity<Object> getPostLikesList(@RequestParam Integer pid){
        try {
            List<LikesResponse> likesList = likesService.getLikesListByPidAndType(pid,0);
            return JsonResponse.generateResponse("查詢貼文按讚列表成功",HttpStatus.OK,likesList);
        }catch (Exception e){
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @RequestMapping(value = "/activityLikesList", method = RequestMethod.GET)
    public ResponseEntity<Object> getActivityLikesList(@RequestParam Integer aid){
        try {
            List<LikesResponse> likesList = likesService.getLikesListByPidAndType(aid,1);
            return JsonResponse.generateResponse("查詢貼文按讚列表成功",HttpStatus.OK,likesList);
        }catch (Exception e){
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

}
