package com.cw.crm.service.impl;

import com.cw.crm.service.ActionSubTypeService;
import com.cw.crm.domain.ActionSubType;
import com.cw.crm.repository.ActionSubTypeRepository;
import com.cw.crm.service.dto.ActionSubTypeDTO;
import com.cw.crm.service.mapper.ActionSubTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ActionSubType.
 */
@Service
@Transactional
public class ActionSubTypeServiceImpl implements ActionSubTypeService {

    private final Logger log = LoggerFactory.getLogger(ActionSubTypeServiceImpl.class);

    private final ActionSubTypeRepository actionSubTypeRepository;

    private final ActionSubTypeMapper actionSubTypeMapper;

    public ActionSubTypeServiceImpl(ActionSubTypeRepository actionSubTypeRepository, ActionSubTypeMapper actionSubTypeMapper) {
        this.actionSubTypeRepository = actionSubTypeRepository;
        this.actionSubTypeMapper = actionSubTypeMapper;
    }

    /**
     * Save a actionSubType.
     *
     * @param actionSubTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ActionSubTypeDTO save(ActionSubTypeDTO actionSubTypeDTO) {
        log.debug("Request to save ActionSubType : {}", actionSubTypeDTO);

        ActionSubType actionSubType = actionSubTypeMapper.toEntity(actionSubTypeDTO);
        actionSubType = actionSubTypeRepository.save(actionSubType);
        return actionSubTypeMapper.toDto(actionSubType);
    }

    /**
     * Get all the actionSubTypes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ActionSubTypeDTO> findAll() {
        log.debug("Request to get all ActionSubTypes");
        return actionSubTypeRepository.findAll().stream()
            .map(actionSubTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one actionSubType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ActionSubTypeDTO> findOne(Long id) {
        log.debug("Request to get ActionSubType : {}", id);
        return actionSubTypeRepository.findById(id)
            .map(actionSubTypeMapper::toDto);
    }

    /**
     * Delete the actionSubType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ActionSubType : {}", id);
        actionSubTypeRepository.deleteById(id);
    }
}
