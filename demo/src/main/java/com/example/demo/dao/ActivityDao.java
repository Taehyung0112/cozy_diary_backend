package com.example.demo.dao;

import com.example.demo.dto.ActivityCoverResponse;
import com.example.demo.dto.PostResponse;
import com.example.demo.entity.Activity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ActivityDao extends JpaRepository<Activity, Integer> {

    public List<Activity> findActivityByHolder(String holder);

    public Activity findActivityByAid(Integer aId);

    @Query(value = "SELECT new com.example.demo.dto.ActivityCoverResponse(a.aid as aid , a.cover as cover , a.likes as likes,  a.activityName as activityName , us.pic as pic , us.name as username , a.activityTime as activityTime , a.placeLng as placeLng , a.placeLat as placeLat) FROM ActivityCategory ac  INNER JOIN Activity a on ac.actId = a.actId INNER JOIN User us on a.holder=us.googleId WHERE ac.actId = :actId and DATE(a.activityTime) > :currentTime Order By a.activityTime DESC")
    public List<ActivityCoverResponse> findActivityCoverByActId(Integer actId , Date currentTime);

    @Query(value = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES\n" +
        "WHERE table_name = 'activity'",nativeQuery = true)
    public Integer findNewAid();

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE activity AUTO_INCREMENT = 1" , nativeQuery = true)
    public void resetActivityId();

    @Query(value = "SELECT new com.example.demo.dto.ActivityCoverResponse(a.aid as aid ,a.cover as cover , a.likes as likes,  a.activityName as activityName , us.pic as pic , us.name as username , a.activityTime as activityTime , a.placeLng as placeLng , a.placeLat as placeLat) FROM Activity a INNER JOIN User us on a.holder=us.googleId where DATE(a.activityTime) > :currentTime ORDER BY a.activityTime DESC")
    public List<ActivityCoverResponse> getActivityListCoverOrderByActivityTime(Date currentTime, Sort sort);

    @Query(value = "SELECT new com.example.demo.dto.ActivityCoverResponse(a.aid as aid ,a.cover as cover , a.likes as likes,  a.activityName as activityName , us.pic as pic , us.name as username , a.activityTime as activityTime , a.placeLng as placeLng , a.placeLat as placeLat) FROM Activity a INNER JOIN User us on a.holder=us.googleId where DATE(a.activityTime) > :currentTime AND a.placeLat = :placeLat AND a.placeLng = :placeLng ORDER BY a.activityTime DESC")
    public List<ActivityCoverResponse> getActivityListCoverByPlaceOrderByActivityTime(Date currentTime, Sort sort , Double placeLat , Double placeLng);


}
