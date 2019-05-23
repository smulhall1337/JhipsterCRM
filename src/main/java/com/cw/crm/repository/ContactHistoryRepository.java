package com.cw.crm.repository;

import com.cw.crm.domain.ContactHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.StreamSupport;


/**
 * Spring Data  repository for the ContactHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactHistoryRepository extends JpaRepository<ContactHistory, Long> {

    @Query(value ="SELECT * FROM CONTACT_HISTORY WHERE PARTICIPANT_ID = ?1",
        nativeQuery = true)
    List<ContactHistory> findAllByParticipantId(Long partId);

    @Query(value ="UPDATE CONTACT_HISTORY set STATUS = 0 WHERE ID = ?1",
        nativeQuery = true)
    void deactivateById(Long id);
}
