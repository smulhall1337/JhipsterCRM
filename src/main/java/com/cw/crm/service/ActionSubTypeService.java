package com.cw.crm.service;

import com.cw.crm.service.dto.ActionSubTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ActionSubType.
 */
public interface ActionSubTypeService {

    /**
     * Save a actionSubType.
     *
     * @param actionSubTypeDTO the entity to save
     * @return the persisted entity
     */
    ActionSubTypeDTO save(ActionSubTypeDTO actionSubTypeDTO);

    /**
     * Get all the actionSubTypes.
     *
     * @return the list of entities
     */
    List<ActionSubTypeDTO> findAll();


    /**
     * Get the "id" actionSubType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ActionSubTypeDTO> findOne(Long id);

    /**
     * Delete the "id" actionSubType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
