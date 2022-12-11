package com.example.demo.dao;

import com.example.demo.entity.RepliesComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RepliesCommentDao extends JpaRepository<RepliesComments,Integer> {
    @Transactional
    @Modifying
    @Query("DELETE  FROM RepliesComments r  WHERE r.commentId = :cid")
    public void deleteRepliesComment(Integer cid);
}
