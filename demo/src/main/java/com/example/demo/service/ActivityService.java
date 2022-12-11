package com.example.demo.service;

import com.example.demo.dto.ActivityCoverResponse;
import com.example.demo.dto.ActivityRequest;
import com.example.demo.dto.ParticipantRequest;
import com.example.demo.dto.ParticipantResponse;
import com.example.demo.entity.Activity;
import com.example.demo.entity.Participant;
import com.example.demo.vo.ParticipantVO;
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

    public Optional<String> updateActivityParticipant(ParticipantVO participantVO);

    public Optional<String> applicationPassed(String uid,Integer aId);

    public List<ParticipantResponse> getParticipantList(Integer aid);

    public List<Participant> getUserParticipatedActivity(String participant);

    public Optional<String> updateActivityLikes(Integer aid,String uid);

    public Optional<String> updateApplication(Integer aid,String uid);

    public List<ActivityCoverResponse> getActivityListByPlace(String sortOption, Double placeLat , Double placeLng);

    public Optional<String> deleteActivity(Integer aid);





//    public Optional<String> updateActivityLikes(Integer aid, String uid);
}
