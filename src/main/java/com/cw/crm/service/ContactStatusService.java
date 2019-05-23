package com.cw.crm.service;

import com.cw.crm.service.dto.ContactStatusDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ContactStatus.
 */
public interface ContactStatusService {

    /**
     * Save a contactStatus.
     *
     * @param contactStatusDTO the entity to save
     * @return the persisted entity
     */
    ContactStatusDTO save(ContactStatusDTO contactStatusDTO);

    /**
     * Get all the contactStatuses.
     *
     * @return the list of entities
     */
    List<ContactStatusDTO> findAll();


    /**
     * Get the "id" contactStatus.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ContactStatusDTO> findOne(Long id);

    /**
     * Delete the "id" contactStatus.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
