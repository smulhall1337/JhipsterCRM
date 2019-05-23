package com.cw.crm.repository;

import com.cw.crm.domain.Participant;
import com.cw.crm.service.dto.ParticipantDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.BitSet;
import java.util.List;
import java.util.stream.DoubleStream;

/**
 * Spring Data  repository for the Participant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    // @Query("select participant from Participant participant where participant.assignedTo.login = ?#{principal.username} and participant.participantStatus = 1")
    // Page<Participant> findByAssignedToIsCurrentUser(Pageable pageable);

//    @Query("select participant from Participant participant where participant.assignedTo.login = ?#{principal.username}")
//    List<Participant> findByAssignedToIsCurrentUser();

    @Query(value ="SELECT * from Participant where Participant.PARTICIPANT_STATUS = 1",
        nativeQuery = true)
    List<Participant> findAllActive();

    //need the @modifying to tell jpa to not use executeQuery()
    @Modifying
    @Query(value ="UPDATE PARTICIPANT SET Participant.PARTICIPANT_STATUS = 0 where Participant.id = ?1",
        nativeQuery = true)
    void deactivateById(Long id);

    @Query(value ="SELECT * from Participant where assigned_to_id = :userId and PARTICIPANT_STATUS = 1",
        nativeQuery = true)
    List<Participant> findAllByAssignedTo(@Param("userId") Long userId);

    @Query(value ="SELECT * from Participant where DATE_AUTHORIZED is not null and AUTHORIZATION_NUMBER is not null and PARTICIPANT_STATUS = 1",
        nativeQuery = true)
    List<Participant> authorizationReport();

}
