package com.cw.crm.service;

import com.cw.crm.service.dto.AltContactDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing AltContact.
 */
public interface AltContactService {

    /**
     * Save a altContact.
     *
     * @param altContactDTO the entity to save
     * @return the persisted entity
     */
    AltContactDTO save(AltContactDTO altContactDTO);

    /**
     * Get all the altContacts.
     *
     * @return the list of entities
     */
    List<AltContactDTO> findAll();


    /**
     * Get the "id" altContact.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AltContactDTO> findOne(Long id);

    /**
     * Delete the "id" altContact.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    List<AltContactDTO> findAllByParticipantId(Long partId);
}
