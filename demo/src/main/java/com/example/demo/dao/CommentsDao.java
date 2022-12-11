package com.example.demo.dao;


import com.example.demo.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsDao extends JpaRepository<Comments,Integer> {

    public List<Comments> getCommentsByPid(Integer pid);

    @Transactional
    @Modifying
    @Query("DELETE  FROM Comments c  WHERE c.pid = :pid")
    public void deleteComment(Integer pid);
}
