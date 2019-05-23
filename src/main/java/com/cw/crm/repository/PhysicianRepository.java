package com.cw.crm.repository;

import com.cw.crm.domain.Physician;
import com.cw.crm.service.dto.PhysicianDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Physician entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhysicianRepository extends JpaRepository<Physician, Long> {

    @Query(value ="SELECT * FROM PHYSICIAN WHERE PARTICIPANT_ID = ?1 and STATUS = 1",
        nativeQuery = true)
    List<Physician> findAllByParticipantId(Long partId);

    @Query(value ="UPDATE PHYSICIAN set STATUS = 0 WHERE ID = ?1",
        nativeQuery = true)
    void deactivateById(Long id);
}
