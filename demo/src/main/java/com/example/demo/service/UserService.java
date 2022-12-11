package com.example.demo.service;

import com.example.demo.dto.FollowerResponse;
import com.example.demo.dto.TrackerResponse;
import com.example.demo.dto.UserRegisterRequest;
import com.example.demo.vo.UserUpdateVO;
import com.example.demo.vo.UserVO;
import com.example.demo.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public User findUserByGoogleId(String gid);

    public Optional<String> register(UserRegisterRequest userRegisterRequest);

    public Optional<String> updateUser(UserUpdateVO userUpdateVO);

    public Optional<String> changeProfilePic(String uid,String fileName);
}
