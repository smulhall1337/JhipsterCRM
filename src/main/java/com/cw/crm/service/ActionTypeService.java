package com.cw.crm.service;

import com.cw.crm.service.dto.ActionTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ActionType.
 */
public interface ActionTypeService {

    /**
     * Save a actionType.
     *
     * @param actionTypeDTO the entity to save
     * @return the persisted entity
     */
    ActionTypeDTO save(ActionTypeDTO actionTypeDTO);

    /**
     * Get all the actionTypes.
     *
     * @return the list of entities
     */
    List<ActionTypeDTO> findAll();


    /**
     * Get the "id" actionType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ActionTypeDTO> findOne(Long id);

    /**
     * Delete the "id" actionType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
