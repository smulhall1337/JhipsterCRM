package com.cw.crm.service.impl;

import com.cw.crm.service.ReferralSourceService;
import com.cw.crm.domain.ReferralSource;
import com.cw.crm.repository.ReferralSourceRepository;
import com.cw.crm.service.dto.ReferralSourceDTO;
import com.cw.crm.service.mapper.ReferralSourceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ReferralSource.
 */
@Service
@Transactional
public class ReferralSourceServiceImpl implements ReferralSourceService {

    private final Logger log = LoggerFactory.getLogger(ReferralSourceServiceImpl.class);

    private final ReferralSourceRepository referralSourceRepository;

    private final ReferralSourceMapper referralSourceMapper;

    public ReferralSourceServiceImpl(ReferralSourceRepository referralSourceRepository, ReferralSourceMapper referralSourceMapper) {
        this.referralSourceRepository = referralSourceRepository;
        this.referralSourceMapper = referralSourceMapper;
    }

    /**
     * Save a referralSource.
     *
     * @param referralSourceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ReferralSourceDTO save(ReferralSourceDTO referralSourceDTO) {
        log.debug("Request to save ReferralSource : {}", referralSourceDTO);

        ReferralSource referralSource = referralSourceMapper.toEntity(referralSourceDTO);
        referralSource = referralSourceRepository.save(referralSource);
        return referralSourceMapper.toDto(referralSource);
    }

    /**
     * Get all the referralSources.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReferralSourceDTO> findAll() {
        log.debug("Request to get all ReferralSources");
        return referralSourceRepository.findAll().stream()
            .map(referralSourceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one referralSource by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ReferralSourceDTO> findOne(Long id) {
        log.debug("Request to get ReferralSource : {}", id);
        return referralSourceRepository.findById(id)
            .map(referralSourceMapper::toDto);
    }

    /**
     * Delete the referralSource by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReferralSource : {}", id);
        referralSourceRepository.deleteById(id);
    }
}
