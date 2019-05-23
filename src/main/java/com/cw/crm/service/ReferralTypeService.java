package com.cw.crm.service;

import com.cw.crm.service.dto.ReferralTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ReferralType.
 */
public interface ReferralTypeService {

    /**
     * Save a referralType.
     *
     * @param referralTypeDTO the entity to save
     * @return the persisted entity
     */
    ReferralTypeDTO save(ReferralTypeDTO referralTypeDTO);

    /**
     * Get all the referralTypes.
     *
     * @return the list of entities
     */
    List<ReferralTypeDTO> findAll();


    /**
     * Get the "id" referralType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ReferralTypeDTO> findOne(Long id);

    /**
     * Delete the "id" referralType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
