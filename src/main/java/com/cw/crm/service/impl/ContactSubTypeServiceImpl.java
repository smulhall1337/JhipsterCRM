package com.cw.crm.service.impl;

import com.cw.crm.service.ContactSubTypeService;
import com.cw.crm.domain.ContactSubType;
import com.cw.crm.repository.ContactSubTypeRepository;
import com.cw.crm.service.dto.ContactSubTypeDTO;
import com.cw.crm.service.mapper.ContactSubTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ContactSubType.
 */
@Service
@Transactional
public class ContactSubTypeServiceImpl implements ContactSubTypeService {

    private final Logger log = LoggerFactory.getLogger(ContactSubTypeServiceImpl.class);

    private final ContactSubTypeRepository contactSubTypeRepository;

    private final ContactSubTypeMapper contactSubTypeMapper;

    public ContactSubTypeServiceImpl(ContactSubTypeRepository contactSubTypeRepository, ContactSubTypeMapper contactSubTypeMapper) {
        this.contactSubTypeRepository = contactSubTypeRepository;
        this.contactSubTypeMapper = contactSubTypeMapper;
    }

    /**
     * Save a contactSubType.
     *
     * @param contactSubTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ContactSubTypeDTO save(ContactSubTypeDTO contactSubTypeDTO) {
        log.debug("Request to save ContactSubType : {}", contactSubTypeDTO);
        ContactSubType contactSubType = contactSubTypeMapper.toEntity(contactSubTypeDTO);
        contactSubType = contactSubTypeRepository.save(contactSubType);
        return contactSubTypeMapper.toDto(contactSubType);
    }

    /**
     * Get all the contactSubTypes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ContactSubTypeDTO> findAll() {
        log.debug("Request to get all ContactSubTypes");
        return contactSubTypeRepository.findAll().stream()
            .map(contactSubTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one contactSubType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ContactSubTypeDTO> findOne(Long id) {
        log.debug("Request to get ContactSubType : {}", id);
        return contactSubTypeRepository.findById(id)
            .map(contactSubTypeMapper::toDto);
    }

    /**
     * Delete the contactSubType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContactSubType : {}", id);
        contactSubTypeRepository.deleteById(id);
    }
}
