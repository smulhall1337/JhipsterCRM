package com.cw.crm.service.impl;

import com.cw.crm.service.AltContactService;
import com.cw.crm.domain.AltContact;
import com.cw.crm.repository.AltContactRepository;
import com.cw.crm.service.dto.AltContactDTO;
import com.cw.crm.service.mapper.AltContactMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing AltContact.
 */
@Service
@Transactional
public class AltContactServiceImpl implements AltContactService {

    private final Logger log = LoggerFactory.getLogger(AltContactServiceImpl.class);

    private final AltContactRepository altContactRepository;

    private final AltContactMapper altContactMapper;

    public AltContactServiceImpl(AltContactRepository altContactRepository, AltContactMapper altContactMapper) {
        this.altContactRepository = altContactRepository;
        this.altContactMapper = altContactMapper;
    }

    /**
     * Save a altContact.
     *
     * @param altContactDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AltContactDTO save(AltContactDTO altContactDTO) {
        log.debug("Request to save AltContact : {}", altContactDTO);
        AltContact altContact = altContactMapper.toEntity(altContactDTO);
        altContact = altContactRepository.save(altContact);
        return altContactMapper.toDto(altContact);
    }

    /**
     * Get all the altContacts.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AltContactDTO> findAll() {
        log.debug("Request to get all AltContacts");
        return altContactRepository.findAll().stream()
            .map(altContactMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one altContact by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AltContactDTO> findOne(Long id) {
        log.debug("Request to get AltContact : {}", id);
        return altContactRepository.findById(id)
            .map(altContactMapper::toDto);
    }

    /**
     * Delete the altContact by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AltContact : {}", id);        altContactRepository.deleteById(id);
    }

    @Override
    public List<AltContactDTO> findAllByParticipantId(Long partId) {
        return altContactRepository.findAllByParticipantId(partId).stream()
            .map(altContactMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


}
