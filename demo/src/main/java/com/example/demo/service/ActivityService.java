package com.example.demo.service;

import com.example.demo.dto.ActivityCoverResponse;
import com.example.demo.dto.ActivityRequest;
import com.example.demo.entity.Activity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ActivityService {

    public List<Activity> getActivityByUid(String uid);

    public Activity getActivityByAid(Integer aid);

    public List<ActivityCoverResponse> getActivityCoverByCategory(Integer actId);

    public Optional<String> addActivity(MultipartFile[] files,ActivityRequest activityRequest);

    public List<ActivityCoverResponse> getActivityListOrderByActivityTime(String sortOption);



//    public Optional<String> updateActivityLikes(Integer aid, String uid);
}
