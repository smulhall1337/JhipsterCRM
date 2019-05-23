package com.cw.crm.service.impl;

import com.cw.crm.service.ContactStatusService;
import com.cw.crm.domain.ContactStatus;
import com.cw.crm.repository.ContactStatusRepository;
import com.cw.crm.service.dto.ContactStatusDTO;
import com.cw.crm.service.mapper.ContactStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ContactStatus.
 */
@Service
@Transactional
public class ContactStatusServiceImpl implements ContactStatusService {

    private final Logger log = LoggerFactory.getLogger(ContactStatusServiceImpl.class);

    private final ContactStatusRepository contactStatusRepository;

    private final ContactStatusMapper contactStatusMapper;

    public ContactStatusServiceImpl(ContactStatusRepository contactStatusRepository, ContactStatusMapper contactStatusMapper) {
        this.contactStatusRepository = contactStatusRepository;
        this.contactStatusMapper = contactStatusMapper;
    }

    /**
     * Save a contactStatus.
     *
     * @param contactStatusDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ContactStatusDTO save(ContactStatusDTO contactStatusDTO) {
        log.debug("Request to save ContactStatus : {}", contactStatusDTO);

        ContactStatus contactStatus = contactStatusMapper.toEntity(contactStatusDTO);
        contactStatus = contactStatusRepository.save(contactStatus);
        return contactStatusMapper.toDto(contactStatus);
    }

    /**
     * Get all the contactStatuses.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ContactStatusDTO> findAll() {
        log.debug("Request to get all ContactStatuses");
        return contactStatusRepository.findAll().stream()
            .map(contactStatusMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one contactStatus by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ContactStatusDTO> findOne(Long id) {
        log.debug("Request to get ContactStatus : {}", id);
        return contactStatusRepository.findById(id)
            .map(contactStatusMapper::toDto);
    }

    /**
     * Delete the contactStatus by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContactStatus : {}", id);
        contactStatusRepository.deleteById(id);
    }
}
