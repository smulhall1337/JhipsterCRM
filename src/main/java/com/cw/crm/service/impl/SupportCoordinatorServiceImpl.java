package com.cw.crm.service.impl;

import com.cw.crm.service.SupportCoordinatorService;
import com.cw.crm.domain.SupportCoordinator;
import com.cw.crm.repository.SupportCoordinatorRepository;
import com.cw.crm.service.dto.SupportCoordinatorDTO;
import com.cw.crm.service.mapper.SupportCoordinatorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing SupportCoordinator.
 */
@Service
@Transactional
public class SupportCoordinatorServiceImpl implements SupportCoordinatorService {

    private final Logger log = LoggerFactory.getLogger(SupportCoordinatorServiceImpl.class);

    private final SupportCoordinatorRepository supportCoordinatorRepository;

    private final SupportCoordinatorMapper supportCoordinatorMapper;

    public SupportCoordinatorServiceImpl(SupportCoordinatorRepository supportCoordinatorRepository, SupportCoordinatorMapper supportCoordinatorMapper) {
        this.supportCoordinatorRepository = supportCoordinatorRepository;
        this.supportCoordinatorMapper = supportCoordinatorMapper;
    }

    /**
     * Save a supportCoordinator.
     *
     * @param supportCoordinatorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SupportCoordinatorDTO save(SupportCoordinatorDTO supportCoordinatorDTO) {
        log.debug("Request to save SupportCoordinator : {}", supportCoordinatorDTO);

        SupportCoordinator supportCoordinator = supportCoordinatorMapper.toEntity(supportCoordinatorDTO);
        supportCoordinator = supportCoordinatorRepository.save(supportCoordinator);
        return supportCoordinatorMapper.toDto(supportCoordinator);
    }

    /**
     * Get all the supportCoordinators.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SupportCoordinatorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SupportCoordinators");
        return supportCoordinatorRepository.findAll(pageable)
            .map(supportCoordinatorMapper::toDto);
    }


    /**
     * Get one supportCoordinator by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SupportCoordinatorDTO> findOne(Long id) {
        log.debug("Request to get SupportCoordinator : {}", id);
        return supportCoordinatorRepository.findById(id)
            .map(supportCoordinatorMapper::toDto);
    }

    /**
     * Delete the supportCoordinator by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SupportCoordinator : {}", id);
        supportCoordinatorRepository.deleteById(id);
    }
}
