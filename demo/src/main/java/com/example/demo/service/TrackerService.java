package com.example.demo.service;

import com.example.demo.dto.TrackerResponse;
import com.example.demo.entity.Tracker;
import com.example.demo.vo.TrackerVO;

import java.util.List;
import java.util.Optional;

public interface TrackerService  {

    public Optional<String> addTracker(TrackerVO trackerVO);

    public Optional<String> deleteTrack(Integer tid);

    public List<TrackerResponse> getTrackerListByGoogleId(String uid);

    public List<TrackerResponse> getFollowerListByGoogleId(String uid);
}