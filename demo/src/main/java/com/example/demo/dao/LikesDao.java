package com.example.demo.dao;


import com.example.demo.dto.LikesResponse;
import com.example.demo.entity.Comments;
import com.example.demo.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LikesDao extends JpaRepository<Likes,Integer> {

    @Query(value = "SELECT new com.example.demo.dto.LikesResponse(us.pic as pic , us.name as username, us.googleId as uid) FROM Likes l INNER JOIN User us on l.uid=us.googleId WHERE l.pid = :pid AND l.type = :type ")
    public List<LikesResponse> getLikesListByPidAndType(Integer pid , Integer type);


    public Likes getLikesByPidAndUidAndType(Integer pid, String uid , Integer type);

    public List<Likes> getLikeListByPidAndType(Integer pid, Integer type);

    @Transactional
    @Modifying
    @Query("DELETE  FROM Likes l  WHERE l.pid = :pid and l.type = 0")
    public void deleteLikes(Integer pid);

    @Transactional
    @Modifying
    @Query("DELETE  FROM Likes l  WHERE l.pid = :pid and l.type = 1")
    public void deleteActivityLikes(Integer pid);
}
