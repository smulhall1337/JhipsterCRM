package com.cw.crm.service.impl;

import com.cw.crm.service.ActionService;
import com.cw.crm.domain.Action;
import com.cw.crm.repository.ActionRepository;
import com.cw.crm.service.dto.ActionDTO;
import com.cw.crm.service.mapper.ActionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;



/**
 * Service Implementation for managing Action.
 */
@Service
@Transactional
public class ActionServiceImpl implements ActionService {

    private final Logger log = LoggerFactory.getLogger(ActionServiceImpl.class);

    private final ActionRepository actionRepository;

    private final ActionMapper actionMapper;

    public ActionServiceImpl(ActionRepository actionRepository, ActionMapper actionMapper) {
        this.actionRepository = actionRepository;
        this.actionMapper = actionMapper;
    }

    /**
     * Save a action.
     *
     * @param actionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ActionDTO save(ActionDTO actionDTO) {
        log.debug("Request to save Action : {}", actionDTO);

        Action action = actionMapper.toEntity(actionDTO);
        action = actionRepository.save(action);
        return actionMapper.toDto(action);
    }

    /**
     * Get all the actions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ActionDTO> findAll() {
        log.debug("Request to get all Actions");
        return actionRepository.findAll().stream()
            .map(actionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one action by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ActionDTO> findOne(Long id) {
        log.debug("Request to get Action : {}", id);
        return actionRepository.findById(id)
            .map(actionMapper::toDto);
    }

    /**
     * Delete the action by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Action : {}", id);
        actionRepository.deleteById(id);
    }

    @Override
    public List<ActionDTO> getAllActionsByUserId(String userId) {
        log.debug("Request to search Actions for query {}", userId);
        return actionRepository.getAllActionsByUserId(userId).stream()
            .map(actionMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<ActionDTO> getAllActionsByParticipantId(String partId) {
        log.debug("Request to search Actions for query {}", partId);
        return actionRepository.getAllActionsByParticipantId(partId).stream()
            .map(actionMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<ActionDTO> getUserActionList(String userId, String endOfDay) {
        return actionRepository.getUserActionList(userId, endOfDay).stream()
            .map(actionMapper::toDto)
            .collect(Collectors.toList());
    }
}
