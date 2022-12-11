package com.example.demo.service;

import com.example.demo.dto.ParticipantResponse;

import java.util.List;

public interface ParticipantService {
    public List<ParticipantResponse> getParticipantListByAid(Integer aid);
}
