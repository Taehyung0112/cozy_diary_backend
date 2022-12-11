package com.example.demo.dao;

import com.example.demo.dto.CategoryResponse;
import com.example.demo.entity.Category;
import com.example.demo.entity.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserCategoryDao extends JpaRepository<UserCategory,Integer> {

    @Query(value = "SELECT new com.example.demo.dto.CategoryResponse(u.uid as uid , u.cid as cid , c.category as category ) FROM UserCategory u  INNER JOIN Category c on u.cid = c.cid  WHERE u.uid = :uid ")
    public List<CategoryResponse> findCategoryListByUid(String uid);

    @Transactional
    @Modifying
    public void  deleteAllByUid(String uid);
}
