package com.cw.crm.service.impl;

import com.cw.crm.service.PhysicianService;
import com.cw.crm.domain.Physician;
import com.cw.crm.repository.PhysicianRepository;
import com.cw.crm.service.dto.PhysicianDTO;
import com.cw.crm.service.mapper.PhysicianMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Physician.
 */
@Service
@Transactional
public class PhysicianServiceImpl implements PhysicianService {

    private final Logger log = LoggerFactory.getLogger(PhysicianServiceImpl.class);

    private final PhysicianRepository physicianRepository;

    private final PhysicianMapper physicianMapper;

    public PhysicianServiceImpl(PhysicianRepository physicianRepository, PhysicianMapper physicianMapper) {
        this.physicianRepository = physicianRepository;
        this.physicianMapper = physicianMapper;
    }

    /**
     * Save a physician.
     *
     * @param physicianDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PhysicianDTO save(PhysicianDTO physicianDTO) {
        log.debug("Request to save Physician : {}", physicianDTO);
        Physician physician = physicianMapper.toEntity(physicianDTO);
        physician = physicianRepository.save(physician);
        return physicianMapper.toDto(physician);
    }

    /**
     * Get all the physicians.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PhysicianDTO> findAll() {
        log.debug("Request to get all Physicians");
        return physicianRepository.findAll().stream()
            .map(physicianMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one physician by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PhysicianDTO> findOne(Long id) {
        log.debug("Request to get Physician : {}", id);
        return physicianRepository.findById(id)
            .map(physicianMapper::toDto);
    }

    /**
     * Delete the physician by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Physician : {}", id);
        physicianRepository.deleteById(id);
    }

    @Override
    public List<PhysicianDTO> findAllByParticipantId(long partId) {
        return physicianRepository.findAllByParticipantId(partId).stream()
            .map(physicianMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public void deactivateById(Long id) {
        log.debug("Request to deactivate Physician : {}", id);
        physicianRepository.deactivateById(id);
    }
}
