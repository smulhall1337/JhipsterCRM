package com.cw.crm.service.impl;

import com.cw.crm.service.MCOService;
import com.cw.crm.domain.MCO;
import com.cw.crm.repository.MCORepository;
import com.cw.crm.service.dto.MCODTO;
import com.cw.crm.service.mapper.MCOMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing MCO.
 */
@Service
@Transactional
public class MCOServiceImpl implements MCOService {

    private final Logger log = LoggerFactory.getLogger(MCOServiceImpl.class);

    private final MCORepository mCORepository;

    private final MCOMapper mCOMapper;

    public MCOServiceImpl(MCORepository mCORepository, MCOMapper mCOMapper) {
        this.mCORepository = mCORepository;
        this.mCOMapper = mCOMapper;
    }

    /**
     * Save a mCO.
     *
     * @param mCODTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MCODTO save(MCODTO mCODTO) {
        log.debug("Request to save MCO : {}", mCODTO);

        MCO mCO = mCOMapper.toEntity(mCODTO);
        mCO = mCORepository.save(mCO);
        return mCOMapper.toDto(mCO);
    }

    /**
     * Get all the mCOS.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MCODTO> findAll() {
        log.debug("Request to get all MCOS");
        return mCORepository.findAll().stream()
            .map(mCOMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one mCO by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MCODTO> findOne(Long id) {
        log.debug("Request to get MCO : {}", id);
        return mCORepository.findById(id)
            .map(mCOMapper::toDto);
    }

    /**
     * Delete the mCO by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MCO : {}", id);
        mCORepository.deleteById(id);
    }
}
