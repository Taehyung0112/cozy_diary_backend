package com.example.demo.dao;

import com.example.demo.dto.TrackerResponse;
import com.example.demo.entity.Follower;
import com.example.demo.entity.Tracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TrackerDao extends JpaRepository<Tracker,Integer> {

    @Query(value = "SELECT new com.example.demo.dto.TrackerResponse(t.tracker1 as tracker1, t.tracker2 as tracker2, u.name as name , t.trackTime as trackTime , u.pic as pic) FROM Tracker t INNER JOIN User u on t.tracker2 = u.googleId WHERE t.tracker1 = :tracker1 ORDER BY t.trackTime DESC")
    public List<TrackerResponse> findTrackerByTracker1(@Param("tracker1") String tracker1);

    @Query(value = "SELECT new com.example.demo.dto.TrackerResponse(t.tracker1 as tracker1, t.tracker2 as tracker2, u.name as name , t.trackTime as trackTime , u.pic as pic) FROM Tracker t INNER JOIN User u on t.tracker1 = u.googleId WHERE t.tracker2 = :tracker2 ORDER BY t.trackTime DESC")
    public List<TrackerResponse> findFollowerByTracker2(@Param("tracker2") String tracker2);

}
