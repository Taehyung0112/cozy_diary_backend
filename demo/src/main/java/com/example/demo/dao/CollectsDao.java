package com.example.demo.dao;

import com.example.demo.entity.Collects;
import com.example.demo.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CollectsDao extends JpaRepository<Collects,Integer> {

    public List<Collects> getCollectListByPid(Integer pid);

    public Collects getCollectsByPidAndUid(Integer pid, String uid);

    @Transactional
    @Modifying
    @Query("DELETE  FROM Collects c  WHERE c.pid = :pid")
    public void deleteCollects(Integer pid);

}
