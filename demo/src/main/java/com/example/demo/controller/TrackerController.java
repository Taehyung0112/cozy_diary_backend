package com.example.demo.controller;

import com.example.demo.dto.TrackerResponse;
import com.example.demo.entity.Tracker;
import com.example.demo.entity.UserCategory;
import com.example.demo.service.TrackerService;
import com.example.demo.util.JsonResponse;
import com.example.demo.vo.TrackerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class TrackerController {

    @Autowired
    private TrackerService trackerService;

    @CrossOrigin(origins = "http://localhost:10001")
    @RequestMapping(value = "/addTracker", method = RequestMethod.POST)
    public ResponseEntity<?> addTracker(@RequestBody TrackerVO trackerVO){
        try{
            Optional<String> optional = trackerService.addTracker(trackerVO);
            return ResponseEntity.ok().body(optional);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }    }

    @CrossOrigin(origins = "http://localhost:10001")
    @RequestMapping(value = "/getTrackerList", method = RequestMethod.GET)
    public ResponseEntity<Object> getTrackerList(@RequestParam String uid){
        try{
            List<TrackerResponse> trackers = trackerService.getTrackerListByGoogleId(uid);
            return JsonResponse.generateResponse("查詢使用者追蹤列表成功", HttpStatus.OK,trackers);
        }catch (Exception e){
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @CrossOrigin(origins = "http://localhost:10001")
    @RequestMapping(value = "/getFollowerList", method = RequestMethod.GET)
    public ResponseEntity<Object> getFollowerList(@RequestParam String uid){
        try{
            List<TrackerResponse> trackers = trackerService.getFollowerListByGoogleId(uid);
            return JsonResponse.generateResponse("查詢使用者粉絲列表成功", HttpStatus.OK,trackers);
        }catch (Exception e){
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
