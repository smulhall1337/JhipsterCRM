package com.cw.crm.service.impl;

import com.cw.crm.service.EnrollmentAgencyService;
import com.cw.crm.domain.EnrollmentAgency;
import com.cw.crm.repository.EnrollmentAgencyRepository;
import com.cw.crm.service.dto.EnrollmentAgencyDTO;
import com.cw.crm.service.mapper.EnrollmentAgencyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing EnrollmentAgency.
 */
@Service
@Transactional
public class EnrollmentAgencyServiceImpl implements EnrollmentAgencyService {

    private final Logger log = LoggerFactory.getLogger(EnrollmentAgencyServiceImpl.class);

    private final EnrollmentAgencyRepository enrollmentAgencyRepository;

    private final EnrollmentAgencyMapper enrollmentAgencyMapper;

    public EnrollmentAgencyServiceImpl(EnrollmentAgencyRepository enrollmentAgencyRepository, EnrollmentAgencyMapper enrollmentAgencyMapper) {
        this.enrollmentAgencyRepository = enrollmentAgencyRepository;
        this.enrollmentAgencyMapper = enrollmentAgencyMapper;
    }

    /**
     * Save a enrollmentAgency.
     *
     * @param enrollmentAgencyDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EnrollmentAgencyDTO save(EnrollmentAgencyDTO enrollmentAgencyDTO) {
        log.debug("Request to save EnrollmentAgency : {}", enrollmentAgencyDTO);

        EnrollmentAgency enrollmentAgency = enrollmentAgencyMapper.toEntity(enrollmentAgencyDTO);
        enrollmentAgency = enrollmentAgencyRepository.save(enrollmentAgency);
        return enrollmentAgencyMapper.toDto(enrollmentAgency);
    }

    /**
     * Get all the enrollmentAgencies.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EnrollmentAgencyDTO> findAll() {
        log.debug("Request to get all EnrollmentAgencies");
        return enrollmentAgencyRepository.findAll().stream()
            .map(enrollmentAgencyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one enrollmentAgency by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EnrollmentAgencyDTO> findOne(Long id) {
        log.debug("Request to get EnrollmentAgency : {}", id);
        return enrollmentAgencyRepository.findById(id)
            .map(enrollmentAgencyMapper::toDto);
    }

    /**
     * Delete the enrollmentAgency by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EnrollmentAgency : {}", id);
        enrollmentAgencyRepository.deleteById(id);
    }
}
