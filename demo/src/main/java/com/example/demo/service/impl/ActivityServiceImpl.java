package com.example.demo.service.impl;

import com.example.demo.dao.ActivityDao;
import com.example.demo.dao.ActivityFilesDao;
import com.example.demo.dao.LikesDao;
import com.example.demo.dao.ParticipantDao;
import com.example.demo.dto.ActivityCoverResponse;
import com.example.demo.dto.ActivityRequest;
import com.example.demo.dto.ParticipantResponse;
import com.example.demo.entity.*;
import com.example.demo.exception.DuplicateCreateException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.ProjectException;
import com.example.demo.service.ActivityService;
import com.example.demo.service.FileStorageService;
import com.example.demo.vo.ParticipantVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Sort;


import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private ActivityFilesDao activityFileDao;

    @Autowired
    private ParticipantDao participantDao;

    @Autowired
    FileStorageService storageService;

    @Autowired
    private LikesDao likesDao;

    @Override
    public List<Activity> getActivityByUid(String uid) {
        return activityDao.findActivityByHolder(uid);
    }

    @Override
    public Activity getActivityByAid(Integer aid) {
        return activityDao.findActivityByAid(aid);
    }

    @Override
    public List<ActivityCoverResponse> getActivityCoverByCategory(Integer actId) {
        LocalDate currentTime = LocalDate.now();
        Date date = Date.from(currentTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return activityDao.findActivityCoverByActId(actId , date);
    }

    @Override
    public Optional<String> addActivity(MultipartFile[] files,ActivityRequest activityRequest) {
//        String basicUrl = "http://172.20.10.10:8080/staticFile/";
        String basicUrl = "http://140.131.114.166:80/staticFile/";

        try{
            Activity activity = new Activity();
            Integer nextAid = 1;
            if(activityDao.findNewAid() == null){
                activityDao.resetActivityId();
            }else {
                nextAid = activityDao.findNewAid();
            }
            List<ActivityFile> activityFiles = new ArrayList<>();
            for(int i = 0; i<files.length;i++){
                ActivityFile activityFile = new ActivityFile();
                activityFile.setActivityUrl(basicUrl+activityRequest.getActivity().getHolder()+"/activityFile/"+nextAid+"/"+files[i].getOriginalFilename());
                activityFiles.add(activityFile);
            }

            activity.setActivityName(activityRequest.getActivity().getActivityName());
            activity.setActivityTime(activityRequest.getActivity().getActivityTime());
            activity.setAuditTime(activityRequest.getActivity().getAuditTime());
            activity.setHolder(activityRequest.getActivity().getHolder());
            activity.setPlaceLat(activityRequest.getActivity().getPlaceLat());
            activity.setPlaceLng(activityRequest.getActivity().getPlaceLng());
            activity.setCover(basicUrl+activityRequest.getActivity().getHolder()+"/activityFile/"+nextAid+"/resize.jpeg");
            activity.setContent(activityRequest.getActivity().getContent());
            activity.setPayment(activityRequest.getActivity().getPayment());
            activity.setBudget(activityRequest.getActivity().getBudget());
            activity.setPublishTime(LocalDateTime.now());
            activity.setLikes(0);
            activity.setParticipants(0);
            activity.setActId(activityRequest.getActivity().getActId());
            activity.setActivityFiles(activityFiles);
            activityDao.save(activity);
            try{
                storageService.saveActivity(files,activity.getAid().toString(),activity.getHolder(),activityRequest.getActivity().getCover());
            }catch (Exception e){
                System.out.println("file upload error:"+e);
                throw new ProjectException(e);
            }
            return Optional.of(activity.getAid().toString());
        }catch (Exception e){
            return Optional.of("新增活動時發生以下錯誤："+e);
        }
    }

    @Override
    public List<ActivityCoverResponse> getActivityListOrderByActivityTime(String sortOption) {
        LocalDate currentTime = LocalDate.now();
        Date date = Date.from(currentTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
        switch (sortOption){
            case "activityTime":
                return activityDao.getActivityListCoverOrderByActivityTime(date,Sort.by(Sort.Direction.ASC,"activityTime"));
            case "publishTime":
                return activityDao.getActivityListCoverOrderByActivityTime(date,Sort.by(Sort.Direction.DESC,"publishTime"));
            default:
                return activityDao.getActivityListCoverOrderByActivityTime(date,Sort.by(Sort.Direction.DESC,"activityTime"));

        }

    }

    @Override
    public Optional<String> updateActivityParticipant(ParticipantVO participantVO) {
        try {
            Participant participant = participantDao.getParticipantByAidAndParticipant(participantVO.getAid(), participantVO.getParticipant());
            Optional<Activity> activity = activityDao.findById(participantVO.getAid());
            if (!activity.isEmpty()){
                Integer newParticipant = activity.get().getParticipants();
                if (Objects.isNull(participant)){
                    Participant participants = new Participant();
                    participants.setParticipant(participantVO.getParticipant());
                    participants.setAid(participantVO.getAid());
                    participants.setQualified(0);
                    participants.setReason(participantVO.getReason());
                    participants.setReleaseTime(LocalDateTime.now().toString());
                    participantDao.save(participants);
                    newParticipant +=1;
                    activity.get().setParticipants(newParticipant);
                    activityDao.save(activity.get());
                    return Optional.of("活動報名成功");
                } else {
                    newParticipant -=1;
                    activity.get().setParticipants(newParticipant);
                    activityDao.save(activity.get());
                    participantDao.delete(participant);
                    return Optional.of("取消報名活動成功");
                }
            } else {
                return Optional.of("活動ID有誤，查無此活動");
            }
        }catch (Exception e){
            System.out.println(e);
            throw new ProjectException(e);
        }
    }

    @Override
    public Optional<String> applicationPassed(String uid, Integer aId) {
        try {
            Participant participant = participantDao.getParticipantByAidAndParticipant(aId, uid);
            if(Objects.nonNull(participant)){
                if(participant.getQualified() == 0){
                    participant.setQualified(1);
                }else {
                    participant.setQualified(0);
                }
                participantDao.save(participant);
                return Optional.of("修改審核狀態成功");
            } else {
                return Optional.of("查無此申請，請檢查帳號或活動ID");
            }
        }catch (Exception e){
            System.out.println(e);
            throw new ProjectException(e);
        }
    }

    @Override
    public List<ParticipantResponse> getParticipantList(Integer aid) {
        return participantDao.getParticipantListByAid(aid);
    }

    @Override
    public List<Participant> getUserParticipatedActivity(String participant) {
        return participantDao.getParticipantByParticipant(participant);
    }

    @Override
    public Optional<String> updateActivityLikes(Integer aid, String uid) {
        try {
            Likes likes = likesDao.getLikesByPidAndUidAndType(aid,uid ,1);
            Optional<Activity> activity = activityDao.findById(aid);
            if(!activity.isEmpty()){
                Integer newLikes = activity.get().getLikes();
                if (Objects.isNull(likes)){
                    Likes addLikes = new Likes();
                    addLikes.setPid(aid);
                    addLikes.setUid(uid);
                    addLikes.setLike_time(LocalDateTime.now().toString());
                    addLikes.setType(1);
                    likesDao.save(addLikes);
                    newLikes +=1;
                    activity.get().setLikes(newLikes);
                    activityDao.save(activity.get());
                    return Optional.of("新增讚數成功");
                } else {
                    newLikes -=1;
                    System.out.println("有此資料");
                    activity.get().setLikes(newLikes);
                    activityDao.save(activity.get());
                    likesDao.delete(likes);
                    return Optional.of("取消讚數成功");
                }
            } else {
                return Optional.of("活動ID有誤，查無此活動");
            }

        }catch (Exception e){
            System.out.println(e);
            throw new ProjectException(e);
        }
    }

    @Override
    public Optional<String> updateApplication(Integer aid, String uid) {
        try {
            Participant participant = participantDao.getParticipantByAidAndParticipant(aid,uid);
            if( participant != null){
                if (participant.getQualified() == 0){
                    participant.setQualified(1);
                } else{
                    participant.setQualified(0);
                }
                participantDao.save(participant);
                return Optional.of("審核狀態修改成功");
            }else {
                return Optional.of("找不到該參與者");
            }
        }catch (Exception e){
            throw new ProjectException(e);
        }
    }

    @Override
    public List<ActivityCoverResponse> getActivityListByPlace(String sortOption, Double placeLat, Double placeLng) {
        LocalDate currentTime = LocalDate.now();
        Date date = Date.from(currentTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
        switch (sortOption){
            case "activityTime":
                return activityDao.getActivityListCoverByPlaceOrderByActivityTime(date,Sort.by(Sort.Direction.ASC,"activityTime"),placeLat,placeLng);
            case "publishTime":
                return activityDao.getActivityListCoverByPlaceOrderByActivityTime(date,Sort.by(Sort.Direction.DESC,"publishTime"),placeLat,placeLng);
            default:
                return activityDao.getActivityListCoverByPlaceOrderByActivityTime(date,Sort.by(Sort.Direction.ASC,"activityTime"),placeLat,placeLng);

        }    }

    @Override
    public Optional<String> deleteActivity(Integer aid) {
        try {
            Activity activity = activityDao.findActivityByAid(aid);
            List<Participant> participants = participantDao.getParticipantByAid(aid);
            String uid ;
            if (activity != null){
                for (int i = 0 ; i < activity.getActivityFiles().size() ; i++){
                    activityFileDao.delete(activity.getActivityFiles().get(i));
                }
                participantDao.deleteParticipant(aid);
                likesDao.deleteActivityLikes(aid);
                activityDao.delete(activity);
                uid = activity.getHolder();
                storageService.deleteActivityAll(uid,aid);
                return Optional.of("刪除活動成功");
            }else {
                return Optional.of("找不到該活動");
            }
        }catch (Exception e){
            throw new DuplicateCreateException("活動刪除失敗："+e);
        }
    }
}
