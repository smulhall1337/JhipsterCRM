package com.cw.crm.service.impl;

import com.cw.crm.service.ExtendedUserService;
import com.cw.crm.domain.ExtendedUser;
import com.cw.crm.repository.ExtendedUserRepository;
import com.cw.crm.service.dto.ExtendedUserDTO;
import com.cw.crm.service.mapper.ExtendedUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ExtendedUser.
 */
@Service
@Transactional
public class ExtendedUserServiceImpl implements ExtendedUserService {

    private final Logger log = LoggerFactory.getLogger(ExtendedUserServiceImpl.class);

    private final ExtendedUserRepository extendedUserRepository;

    private final ExtendedUserMapper extendedUserMapper;

    public ExtendedUserServiceImpl(ExtendedUserRepository extendedUserRepository, ExtendedUserMapper extendedUserMapper) {
        this.extendedUserRepository = extendedUserRepository;
        this.extendedUserMapper = extendedUserMapper;
    }

    /**
     * Save a extendedUser.
     *
     * @param extendedUserDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ExtendedUserDTO save(ExtendedUserDTO extendedUserDTO) {
        log.debug("Request to save ExtendedUser : {}", extendedUserDTO);

        ExtendedUser extendedUser = extendedUserMapper.toEntity(extendedUserDTO);
        extendedUser = extendedUserRepository.save(extendedUser);
        return extendedUserMapper.toDto(extendedUser);
    }

    /**
     * Get all the extendedUsers.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ExtendedUserDTO> findAll() {
        log.debug("Request to get all ExtendedUsers");
        return extendedUserRepository.findAll().stream()
            .map(extendedUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one extendedUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ExtendedUserDTO> findOne(Long id) {
        log.debug("Request to get ExtendedUser : {}", id);
        return extendedUserRepository.findById(id)
            .map(extendedUserMapper::toDto);
    }

    /**
     * Delete the extendedUser by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExtendedUser : {}", id);
        extendedUserRepository.deleteById(id);
    }
}
