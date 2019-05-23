package com.cw.crm.service.impl;

import com.cw.crm.service.PriorityService;
import com.cw.crm.domain.Priority;
import com.cw.crm.repository.PriorityRepository;
import com.cw.crm.service.dto.PriorityDTO;
import com.cw.crm.service.mapper.PriorityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Priority.
 */
@Service
@Transactional
public class PriorityServiceImpl implements PriorityService {

    private final Logger log = LoggerFactory.getLogger(PriorityServiceImpl.class);

    private final PriorityRepository priorityRepository;

    private final PriorityMapper priorityMapper;

    public PriorityServiceImpl(PriorityRepository priorityRepository, PriorityMapper priorityMapper) {
        this.priorityRepository = priorityRepository;
        this.priorityMapper = priorityMapper;
    }

    /**
     * Save a priority.
     *
     * @param priorityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PriorityDTO save(PriorityDTO priorityDTO) {
        log.debug("Request to save Priority : {}", priorityDTO);

        Priority priority = priorityMapper.toEntity(priorityDTO);
        priority = priorityRepository.save(priority);
        return priorityMapper.toDto(priority);
    }

    /**
     * Get all the priorities.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PriorityDTO> findAll() {
        log.debug("Request to get all Priorities");
        return priorityRepository.findAll().stream()
            .map(priorityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one priority by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PriorityDTO> findOne(Long id) {
        log.debug("Request to get Priority : {}", id);
        return priorityRepository.findById(id)
            .map(priorityMapper::toDto);
    }

    /**
     * Delete the priority by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Priority : {}", id);
        priorityRepository.deleteById(id);
    }
}
