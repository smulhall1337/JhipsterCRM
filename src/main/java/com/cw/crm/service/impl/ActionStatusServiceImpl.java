package com.cw.crm.service.impl;

import com.cw.crm.service.ActionStatusService;
import com.cw.crm.domain.ActionStatus;
import com.cw.crm.repository.ActionStatusRepository;
import com.cw.crm.service.dto.ActionStatusDTO;
import com.cw.crm.service.mapper.ActionStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ActionStatus.
 */
@Service
@Transactional
public class ActionStatusServiceImpl implements ActionStatusService {

    private final Logger log = LoggerFactory.getLogger(ActionStatusServiceImpl.class);

    private final ActionStatusRepository actionStatusRepository;

    private final ActionStatusMapper actionStatusMapper;

    public ActionStatusServiceImpl(ActionStatusRepository actionStatusRepository, ActionStatusMapper actionStatusMapper) {
        this.actionStatusRepository = actionStatusRepository;
        this.actionStatusMapper = actionStatusMapper;
    }

    /**
     * Save a actionStatus.
     *
     * @param actionStatusDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ActionStatusDTO save(ActionStatusDTO actionStatusDTO) {
        log.debug("Request to save ActionStatus : {}", actionStatusDTO);

        ActionStatus actionStatus = actionStatusMapper.toEntity(actionStatusDTO);
        actionStatus = actionStatusRepository.save(actionStatus);
        return actionStatusMapper.toDto(actionStatus);
    }

    /**
     * Get all the actionStatuses.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ActionStatusDTO> findAll() {
        log.debug("Request to get all ActionStatuses");
        return actionStatusRepository.findAll().stream()
            .map(actionStatusMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one actionStatus by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ActionStatusDTO> findOne(Long id) {
        log.debug("Request to get ActionStatus : {}", id);
        return actionStatusRepository.findById(id)
            .map(actionStatusMapper::toDto);
    }

    /**
     * Delete the actionStatus by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ActionStatus : {}", id);
        actionStatusRepository.deleteById(id);
    }
}
