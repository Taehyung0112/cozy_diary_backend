package com.example.demo.service.impl;

import com.example.demo.dao.TrackerDao;
import com.example.demo.dto.TrackerResponse;
import com.example.demo.entity.Tracker;
import com.example.demo.exception.NotFoundException;
import com.example.demo.service.TrackerService;
import com.example.demo.vo.TrackerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TrackerServiceImpl implements TrackerService{

    @Autowired
    private TrackerDao trackerDao;

    @Override
    public Optional<String> addTracker(TrackerVO trackerVO){
        try{
            Tracker tracker = new Tracker();
            tracker.setTracker1(trackerVO.getTracker1());
            tracker.setTracker2(trackerVO.getTracker2());
            tracker.setTrackTime(LocalDateTime.now());
            trackerDao.save(tracker);

            return Optional.of("新增追蹤成功");
        }catch (Exception e){
            return Optional.of("新增追蹤發生以下錯誤："+e);
        }
    }

    @Override
    public Optional<String> deleteTrack(Integer tid) {
        Tracker tracker = trackerDao.getById(tid);
        try{
            trackerDao.delete(tracker);
            return Optional.of("刪除追蹤成功");
        }catch (Exception e){
            return Optional.of("刪除追蹤發生以下錯誤："+e);
        }
    }

    @Override
    public List<TrackerResponse> getTrackerListByGoogleId(String uid) {
        List<TrackerResponse> trackers = trackerDao.findTrackerByTracker1(uid);
        if(trackers != null){
            return trackers;
        }else {
            throw new NotFoundException("目前無追蹤任何帳號");
        }
    }

    @Override
    public List<TrackerResponse> getFollowerListByGoogleId(String uid) {
        List<TrackerResponse> trackers = trackerDao.findFollowerByTracker2(uid);
        if(trackers != null){
            return trackers;
        }else {
            throw new NotFoundException("目前無任何粉絲");
        }
    }
}
