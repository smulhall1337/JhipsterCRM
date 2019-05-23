package com.cw.crm.repository;

import com.cw.crm.domain.ParticipantNotes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ParticipantNotes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParticipantNotesRepository extends JpaRepository<ParticipantNotes, Long> {

    @Query("select participant_notes from ParticipantNotes participant_notes where participant_notes.user.login = ?#{principal.username}")
    List<ParticipantNotes> findByUserIsCurrentUser();

}
