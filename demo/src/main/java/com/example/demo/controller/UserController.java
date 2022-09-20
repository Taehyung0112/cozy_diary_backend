package com.example.demo.controller;



import com.example.demo.dto.UserRegisterRequest;
import com.example.demo.entity.User;
import com.example.demo.service.FileStorageService;
import com.example.demo.service.UserService;
import com.example.demo.util.JsonResponse;
import com.example.demo.vo.UserVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    FileStorageService storageService;


    ObjectMapper objectMapper = new ObjectMapper();


    @CrossOrigin(origins = "http://172.20.10.8:8080")
    @RequestMapping(value = "/userRegister", method = RequestMethod.POST)
    public ResponseEntity<?> doRegister(@RequestParam(required=true, value="file") MultipartFile file, @RequestParam(required=true, value="jsondata") String jsonData) {
        try {
            objectMapper.registerModule(new JSR310Module());
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
            userRegisterRequest = objectMapper.readValue(jsonData, UserRegisterRequest.class);
            Optional<String> optional = userService.register(userRegisterRequest);
            try {
                storageService.saveProfile(file,userRegisterRequest.getUser().getGoogleId());
            }catch (Exception e){
                System.out.println("file upload error:"+e);
            }
            return JsonResponse.generateResponse(optional.get(), HttpStatus.OK, "");
        } catch (Exception e) {
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @CrossOrigin(origins = "http://172.20.10.8:8080")
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public ResponseEntity<Object> getUserByGid(@RequestParam String gid) {
        try {
            User user = userService.findUserByGoogleId(gid);
            return JsonResponse.generateResponse("獲取用戶基本資料成功", HttpStatus.OK, user);
        } catch (Exception e) {
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @CrossOrigin(origins = "http://localhost:10001")
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public ResponseEntity<?> updateUser(@RequestBody UserVO userVO) {
        try {
            Optional<String> optional = userService.updateUser(userVO);
            return JsonResponse.generateResponse(optional.get(), HttpStatus.OK, "");
        } catch (Exception e) {
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
