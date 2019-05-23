package com.cw.crm.service;

import com.cw.crm.service.dto.EmployeeTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing EmployeeType.
 */
public interface EmployeeTypeService {

    /**
     * Save a employeeType.
     *
     * @param employeeTypeDTO the entity to save
     * @return the persisted entity
     */
    EmployeeTypeDTO save(EmployeeTypeDTO employeeTypeDTO);

    /**
     * Get all the employeeTypes.
     *
     * @return the list of entities
     */
    List<EmployeeTypeDTO> findAll();


    /**
     * Get the "id" employeeType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EmployeeTypeDTO> findOne(Long id);

    /**
     * Delete the "id" employeeType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
