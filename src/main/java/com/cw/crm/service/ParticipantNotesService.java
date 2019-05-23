package com.cw.crm.service;

import com.cw.crm.service.dto.ParticipantNotesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ParticipantNotes.
 */
public interface ParticipantNotesService {

    /**
     * Save a participantNotes.
     *
     * @param participantNotesDTO the entity to save
     * @return the persisted entity
     */
    ParticipantNotesDTO save(ParticipantNotesDTO participantNotesDTO);

    /**
     * Get all the participantNotes.
     *
     * @return the list of entities
     */
    List<ParticipantNotesDTO> findAll();


    /**
     * Get the "id" participantNotes.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ParticipantNotesDTO> findOne(Long id);

    /**
     * Delete the "id" participantNotes.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
