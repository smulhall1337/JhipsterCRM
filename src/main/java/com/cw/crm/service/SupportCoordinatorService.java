package com.cw.crm.service;

import com.cw.crm.service.dto.SupportCoordinatorDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing SupportCoordinator.
 */
public interface SupportCoordinatorService {

    /**
     * Save a supportCoordinator.
     *
     * @param supportCoordinatorDTO the entity to save
     * @return the persisted entity
     */
    SupportCoordinatorDTO save(SupportCoordinatorDTO supportCoordinatorDTO);

    /**
     * Get all the supportCoordinators.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SupportCoordinatorDTO> findAll(Pageable pageable);


    /**
     * Get the "id" supportCoordinator.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SupportCoordinatorDTO> findOne(Long id);

    /**
     * Delete the "id" supportCoordinator.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
