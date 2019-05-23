package com.cw.crm.service;

import com.cw.crm.service.dto.ContactSubTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ContactSubType.
 */
public interface ContactSubTypeService {

    /**
     * Save a contactSubType.
     *
     * @param contactSubTypeDTO the entity to save
     * @return the persisted entity
     */
    ContactSubTypeDTO save(ContactSubTypeDTO contactSubTypeDTO);

    /**
     * Get all the contactSubTypes.
     *
     * @return the list of entities
     */
    List<ContactSubTypeDTO> findAll();


    /**
     * Get the "id" contactSubType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ContactSubTypeDTO> findOne(Long id);

    /**
     * Delete the "id" contactSubType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
