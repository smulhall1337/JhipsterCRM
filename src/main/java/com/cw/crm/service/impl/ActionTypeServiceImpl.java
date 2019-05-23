package com.cw.crm.service.impl;

import com.cw.crm.service.ActionTypeService;
import com.cw.crm.domain.ActionType;
import com.cw.crm.repository.ActionTypeRepository;
import com.cw.crm.service.dto.ActionTypeDTO;
import com.cw.crm.service.mapper.ActionTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ActionType.
 */
@Service
@Transactional
public class ActionTypeServiceImpl implements ActionTypeService {

    private final Logger log = LoggerFactory.getLogger(ActionTypeServiceImpl.class);

    private final ActionTypeRepository actionTypeRepository;

    private final ActionTypeMapper actionTypeMapper;

    public ActionTypeServiceImpl(ActionTypeRepository actionTypeRepository, ActionTypeMapper actionTypeMapper) {
        this.actionTypeRepository = actionTypeRepository;
        this.actionTypeMapper = actionTypeMapper;
    }

    /**
     * Save a actionType.
     *
     * @param actionTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ActionTypeDTO save(ActionTypeDTO actionTypeDTO) {
        log.debug("Request to save ActionType : {}", actionTypeDTO);

        ActionType actionType = actionTypeMapper.toEntity(actionTypeDTO);
        actionType = actionTypeRepository.save(actionType);
        return actionTypeMapper.toDto(actionType);
    }

    /**
     * Get all the actionTypes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ActionTypeDTO> findAll() {
        log.debug("Request to get all ActionTypes");
        return actionTypeRepository.findAll().stream()
            .map(actionTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one actionType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ActionTypeDTO> findOne(Long id) {
        log.debug("Request to get ActionType : {}", id);
        return actionTypeRepository.findById(id)
            .map(actionTypeMapper::toDto);
    }

    /**
     * Delete the actionType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ActionType : {}", id);
        actionTypeRepository.deleteById(id);
    }
}
