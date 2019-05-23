package com.cw.crm.service;

import com.cw.crm.service.dto.PriorityDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Priority.
 */
public interface PriorityService {

    /**
     * Save a priority.
     *
     * @param priorityDTO the entity to save
     * @return the persisted entity
     */
    PriorityDTO save(PriorityDTO priorityDTO);

    /**
     * Get all the priorities.
     *
     * @return the list of entities
     */
    List<PriorityDTO> findAll();


    /**
     * Get the "id" priority.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PriorityDTO> findOne(Long id);

    /**
     * Delete the "id" priority.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
