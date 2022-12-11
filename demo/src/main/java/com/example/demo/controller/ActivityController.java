package com.example.demo.controller;

import com.example.demo.dto.ActivityCoverResponse;
import com.example.demo.dto.ActivityRequest;
import com.example.demo.dto.ParticipantResponse;
import com.example.demo.entity.Activity;
import com.example.demo.entity.Participant;
import com.example.demo.service.ActivityService;
import com.example.demo.service.FileStorageService;
import com.example.demo.util.JsonResponse;
import com.example.demo.vo.ParticipantVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @Autowired
    FileStorageService storageService;

    ObjectMapper objectMapper = new ObjectMapper()
        .registerModule(new ParameterNamesModule())
        .registerModule(new Jdk8Module())
        .registerModule(new JavaTimeModule());;

    @CrossOrigin(origins = "http://localhost:10001")
    @RequestMapping(value = "/addActivity", method = RequestMethod.POST)
    public ResponseEntity<?> addActivity(@RequestParam(required=true, value="file") MultipartFile[] file, @RequestParam(required=true, value="jsondata") String jsonData){
        try {
            ActivityRequest activityRequest = new ActivityRequest();
            activityRequest = objectMapper.readValue(jsonData, ActivityRequest.class);
            Optional<String> optional = activityService.addActivity(file,activityRequest);
//            try {
//                storageService.saveActivity(file,optional.get(),activityRequest.getActivity().getHolder());
//            }catch (Exception e){
//                System.out.println("file upload error:"+e);
//            }
            return JsonResponse.generateResponse(optional.get(), HttpStatus.OK,"");
        }catch (Exception e){
            System.out.println(e);
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @CrossOrigin(origins = "http://172.20.10.10:8080")
    @RequestMapping(value = "/getActivityCoverByCategory", method = RequestMethod.GET)
    public ResponseEntity<Object> getActivityCoverByCategory(@RequestParam Integer acid){
        try{
            List<ActivityCoverResponse> activityCoverResponses = activityService.getActivityCoverByCategory(acid);
            return JsonResponse.generateResponse("獲取用戶活動成功", HttpStatus.OK, activityCoverResponses);
        }catch (Exception e){
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @CrossOrigin(origins = "http://172.20.10.10:8080")
    @RequestMapping(value = "/getActivityCover", method = RequestMethod.GET)
    public ResponseEntity<Object> getActivityCover(@RequestParam String option){
        try{
            List<ActivityCoverResponse> activityCoverResponses = activityService.getActivityListOrderByActivityTime(option);
            return JsonResponse.generateResponse("獲取用戶活動成功", HttpStatus.OK, activityCoverResponses);
        }catch (Exception e){
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @CrossOrigin(origins = "http://172.20.10.10:8080")
    @RequestMapping(value = "/getActivityDetail", method = RequestMethod.GET)
    public ResponseEntity<Object> getActivityDetail(@RequestParam Integer aid){
        try{
            Activity activityDetailResponses = activityService.getActivityByAid(aid);
            return JsonResponse.generateResponse("獲取用戶活動成功", HttpStatus.OK, activityDetailResponses);
        }catch (Exception e){
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @CrossOrigin(origins = "http://localhost:10001")
    @RequestMapping(value = "/updateParticipant", method = RequestMethod.POST)
    public ResponseEntity<?> updateParticipant(@RequestBody ParticipantVO participantVO){
        try {
            Optional<String> optional = activityService.updateActivityParticipant(participantVO);
            return JsonResponse.generateResponse(optional.get(), HttpStatus.OK,"");
        }catch (Exception e){
            System.out.println(e);
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @CrossOrigin(origins = "http://172.20.10.10:8080")
    @RequestMapping(value = "/getParticipantList", method = RequestMethod.GET)
    public ResponseEntity<Object> getParticipantList(@RequestParam Integer aid){
        try{
            List<ParticipantResponse> participantResponses = activityService.getParticipantList(aid);
            return JsonResponse.generateResponse("獲取用戶活動成功", HttpStatus.OK, participantResponses);
        }catch (Exception e){
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @CrossOrigin(origins = "http://172.20.10.10:8080")
    @RequestMapping(value = "/getUserParticipated", method = RequestMethod.GET)
    public ResponseEntity<Object> getUserParticipated(@RequestParam String uid){
        try{
            List<Participant> participantResponses = activityService.getUserParticipatedActivity(uid);
            return JsonResponse.generateResponse("獲取用戶活動成功", HttpStatus.OK, participantResponses);
        }catch (Exception e){
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @CrossOrigin(origins = "http://172.20.10.10:8080")
    @RequestMapping(value = "/updateApplication", method = RequestMethod.GET)
    public ResponseEntity<Object> updateApplication(@RequestParam String uid, Integer aid){
        try{
            Optional<String> optional = activityService.applicationPassed(uid, aid);
            return JsonResponse.generateResponse(optional.get(), HttpStatus.OK,"");
        }catch (Exception e){
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @CrossOrigin(origins = "http://localhost:10001")
    @RequestMapping(value = "/updateActivityLikes", method = RequestMethod.POST)
    public ResponseEntity<?> updateLikes(@RequestParam Integer aid, @RequestParam String uid){
        try {
            Optional<String> optional = activityService.updateActivityLikes(aid,uid);
            return JsonResponse.generateResponse(optional.get(), HttpStatus.OK,"");
        }catch (Exception e){
            System.out.println(e);
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @CrossOrigin(origins = "http://localhost:10001")
    @RequestMapping(value = "/updateApplication", method = RequestMethod.POST)
    public ResponseEntity<?> updateApplication(@RequestParam Integer aid, @RequestParam String uid){
        try {
            Optional<String> optional = activityService.updateApplication(aid,uid);
            return JsonResponse.generateResponse(optional.get(), HttpStatus.OK,"");
        }catch (Exception e){
            System.out.println(e);
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @CrossOrigin(origins = "http://172.20.10.10:8080")
    @RequestMapping(value = "/getActivityCoverByPlace", method = RequestMethod.GET)
    public ResponseEntity<Object> getActivityCoverByPlace(@RequestParam String option , Double placeLat , Double placeLng){
        try{
            List<ActivityCoverResponse> activityCoverResponses = activityService.getActivityListByPlace(option,placeLat,placeLng);
            return JsonResponse.generateResponse("獲取用戶活動成功", HttpStatus.OK, activityCoverResponses);
        }catch (Exception e){
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @CrossOrigin(origins = "http://localhost:10001")
    @RequestMapping(value = "/deleteActivity", method = RequestMethod.POST)
    public ResponseEntity<?> deleteActivity(@RequestParam Integer aid){
        try {
            Optional<String> optional = activityService.deleteActivity(aid);
            return JsonResponse.generateResponse(optional.get(), HttpStatus.OK,"");
        }catch (Exception e){
            System.out.println(e);
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
