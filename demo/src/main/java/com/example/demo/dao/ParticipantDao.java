package com.example.demo.dao;

import com.example.demo.dto.ParticipantResponse;
import com.example.demo.entity.Likes;
import com.example.demo.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ParticipantDao extends JpaRepository<Participant,Integer> {

    @Query(value = "SELECT new com.example.demo.dto.ParticipantResponse(p.partId as partId , p.participant as participant , p.qualified , p.reason as reason , p.releaseTime as releaseTime , us.pic as pic , us.name as name) FROM Participant p INNER JOIN User us on p.participant=us.googleId WHERE p.aid = :aid ")
    public List<ParticipantResponse> getParticipantListByAid(Integer aid);

    public List<Participant> getParticipantByAid(Integer aid);

    public Participant getParticipantByAidAndParticipant(Integer aid, String participant);

    public List<Participant> getParticipantByParticipant(String participant);


    @Transactional
    @Modifying
    @Query("DELETE  FROM Participant p  WHERE p.aid = :aid")
    public void deleteParticipant(Integer aid);

}
