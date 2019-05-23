package com.cw.crm.service;

import com.cw.crm.service.dto.ContactSubStatusDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ContactSubStatus.
 */
public interface ContactSubStatusService {

    /**
     * Save a contactSubStatus.
     *
     * @param contactSubStatusDTO the entity to save
     * @return the persisted entity
     */
    ContactSubStatusDTO save(ContactSubStatusDTO contactSubStatusDTO);

    /**
     * Get all the contactSubStatuses.
     *
     * @return the list of entities
     */
    List<ContactSubStatusDTO> findAll();


    /**
     * Get the "id" contactSubStatus.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ContactSubStatusDTO> findOne(Long id);

    /**
     * Delete the "id" contactSubStatus.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
