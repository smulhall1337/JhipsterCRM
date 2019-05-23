package com.cw.crm.service;

import com.cw.crm.service.dto.ActionStatusDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ActionStatus.
 */
public interface ActionStatusService {

    /**
     * Save a actionStatus.
     *
     * @param actionStatusDTO the entity to save
     * @return the persisted entity
     */
    ActionStatusDTO save(ActionStatusDTO actionStatusDTO);

    /**
     * Get all the actionStatuses.
     *
     * @return the list of entities
     */
    List<ActionStatusDTO> findAll();


    /**
     * Get the "id" actionStatus.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ActionStatusDTO> findOne(Long id);

    /**
     * Delete the "id" actionStatus.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
