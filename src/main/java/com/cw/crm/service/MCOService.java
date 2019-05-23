package com.cw.crm.service;

import com.cw.crm.service.dto.MCODTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing MCO.
 */
public interface MCOService {

    /**
     * Save a mCO.
     *
     * @param mCODTO the entity to save
     * @return the persisted entity
     */
    MCODTO save(MCODTO mCODTO);

    /**
     * Get all the mCOS.
     *
     * @return the list of entities
     */
    List<MCODTO> findAll();


    /**
     * Get the "id" mCO.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MCODTO> findOne(Long id);

    /**
     * Delete the "id" mCO.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
