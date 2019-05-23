package com.cw.crm.service.impl;

import com.cw.crm.service.ContactSubStatusService;
import com.cw.crm.domain.ContactSubStatus;
import com.cw.crm.repository.ContactSubStatusRepository;
import com.cw.crm.service.dto.ContactSubStatusDTO;
import com.cw.crm.service.mapper.ContactSubStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ContactSubStatus.
 */
@Service
@Transactional
public class ContactSubStatusServiceImpl implements ContactSubStatusService {

    private final Logger log = LoggerFactory.getLogger(ContactSubStatusServiceImpl.class);

    private final ContactSubStatusRepository contactSubStatusRepository;

    private final ContactSubStatusMapper contactSubStatusMapper;

    public ContactSubStatusServiceImpl(ContactSubStatusRepository contactSubStatusRepository, ContactSubStatusMapper contactSubStatusMapper) {
        this.contactSubStatusRepository = contactSubStatusRepository;
        this.contactSubStatusMapper = contactSubStatusMapper;
    }

    /**
     * Save a contactSubStatus.
     *
     * @param contactSubStatusDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ContactSubStatusDTO save(ContactSubStatusDTO contactSubStatusDTO) {
        log.debug("Request to save ContactSubStatus : {}", contactSubStatusDTO);

        ContactSubStatus contactSubStatus = contactSubStatusMapper.toEntity(contactSubStatusDTO);
        contactSubStatus = contactSubStatusRepository.save(contactSubStatus);
        return contactSubStatusMapper.toDto(contactSubStatus);
    }

    /**
     * Get all the contactSubStatuses.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ContactSubStatusDTO> findAll() {
        log.debug("Request to get all ContactSubStatuses");
        return contactSubStatusRepository.findAll().stream()
            .map(contactSubStatusMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one contactSubStatus by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ContactSubStatusDTO> findOne(Long id) {
        log.debug("Request to get ContactSubStatus : {}", id);
        return contactSubStatusRepository.findById(id)
            .map(contactSubStatusMapper::toDto);
    }

    /**
     * Delete the contactSubStatus by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContactSubStatus : {}", id);
        contactSubStatusRepository.deleteById(id);
    }
}
