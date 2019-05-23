package com.cw.crm.repository;

import com.cw.crm.domain.AltContact;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.BitSet;
import java.util.List;

/**
 * Spring Data  repository for the AltContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AltContactRepository extends JpaRepository<AltContact, Long> {

    @Query("select alt_contact from AltContact alt_contact where alt_contact.createdBy.login = ?#{principal.username}")
    List<AltContact> findByCreatedByIsCurrentUser();

    @Query(value ="SELECT * FROM ALT_CONTACT WHERE PARTICIPANT_ID = ?1",
        nativeQuery = true)
    List<AltContact>  findAllByParticipantId(Long partId);

    //need the @modifying to tell jpa to not use executeQuery()
    @Modifying
    @Query(value ="UPDATE ALT_CONTACT SET STATUS = 0 where ID = ?1",
        nativeQuery = true)
    void deactivateById(Long id);
}
