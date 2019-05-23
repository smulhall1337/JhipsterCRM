package com.cw.crm.service;

import com.cw.crm.service.dto.EmployeeSubTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing EmployeeSubType.
 */
public interface EmployeeSubTypeService {

    /**
     * Save a employeeSubType.
     *
     * @param employeeSubTypeDTO the entity to save
     * @return the persisted entity
     */
    EmployeeSubTypeDTO save(EmployeeSubTypeDTO employeeSubTypeDTO);

    /**
     * Get all the employeeSubTypes.
     *
     * @return the list of entities
     */
    List<EmployeeSubTypeDTO> findAll();


    /**
     * Get the "id" employeeSubType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EmployeeSubTypeDTO> findOne(Long id);

    /**
     * Delete the "id" employeeSubType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
