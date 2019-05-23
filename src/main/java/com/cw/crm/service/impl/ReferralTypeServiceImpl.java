package com.cw.crm.service.impl;

import com.cw.crm.service.ReferralTypeService;
import com.cw.crm.domain.ReferralType;
import com.cw.crm.repository.ReferralTypeRepository;
import com.cw.crm.service.dto.ReferralTypeDTO;
import com.cw.crm.service.mapper.ReferralTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ReferralType.
 */
@Service
@Transactional
public class ReferralTypeServiceImpl implements ReferralTypeService {

    private final Logger log = LoggerFactory.getLogger(ReferralTypeServiceImpl.class);

    private final ReferralTypeRepository referralTypeRepository;

    private final ReferralTypeMapper referralTypeMapper;

    public ReferralTypeServiceImpl(ReferralTypeRepository referralTypeRepository, ReferralTypeMapper referralTypeMapper) {
        this.referralTypeRepository = referralTypeRepository;
        this.referralTypeMapper = referralTypeMapper;
    }

    /**
     * Save a referralType.
     *
     * @param referralTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ReferralTypeDTO save(ReferralTypeDTO referralTypeDTO) {
        log.debug("Request to save ReferralType : {}", referralTypeDTO);

        ReferralType referralType = referralTypeMapper.toEntity(referralTypeDTO);
        referralType = referralTypeRepository.save(referralType);
        return referralTypeMapper.toDto(referralType);
    }

    /**
     * Get all the referralTypes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReferralTypeDTO> findAll() {
        log.debug("Request to get all ReferralTypes");
        return referralTypeRepository.findAll().stream()
            .map(referralTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one referralType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ReferralTypeDTO> findOne(Long id) {
        log.debug("Request to get ReferralType : {}", id);
        return referralTypeRepository.findById(id)
            .map(referralTypeMapper::toDto);
    }

    /**
     * Delete the referralType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReferralType : {}", id);
        referralTypeRepository.deleteById(id);
    }
}
