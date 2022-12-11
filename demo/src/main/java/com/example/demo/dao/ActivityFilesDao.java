package com.example.demo.dao;

import com.example.demo.entity.ActivityFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityFilesDao extends JpaRepository<ActivityFile, Integer> {
}
