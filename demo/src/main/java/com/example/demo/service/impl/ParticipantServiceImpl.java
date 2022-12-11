package com.example.demo.service.impl;

import com.example.demo.dao.ParticipantDao;
import com.example.demo.dto.ParticipantResponse;
import com.example.demo.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantServiceImpl implements ParticipantService {
    @Autowired
    private ParticipantDao participantDao;

    @Override
    public List<ParticipantResponse> getParticipantListByAid(Integer aid) {
        return participantDao.getParticipantListByAid(aid);
    }
}
