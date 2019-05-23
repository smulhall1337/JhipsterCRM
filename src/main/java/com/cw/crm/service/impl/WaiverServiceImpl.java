package com.cw.crm.service.impl;

import com.cw.crm.service.WaiverService;
import com.cw.crm.domain.Waiver;
import com.cw.crm.repository.WaiverRepository;
import com.cw.crm.service.dto.WaiverDTO;
import com.cw.crm.service.mapper.WaiverMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Waiver.
 */
@Service
@Transactional
public class WaiverServiceImpl implements WaiverService {

    private final Logger log = LoggerFactory.getLogger(WaiverServiceImpl.class);

    private final WaiverRepository waiverRepository;

    private final WaiverMapper waiverMapper;

    public WaiverServiceImpl(WaiverRepository waiverRepository, WaiverMapper waiverMapper) {
        this.waiverRepository = waiverRepository;
        this.waiverMapper = waiverMapper;
    }

    /**
     * Save a waiver.
     *
     * @param waiverDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WaiverDTO save(WaiverDTO waiverDTO) {
        log.debug("Request to save Waiver : {}", waiverDTO);

        Waiver waiver = waiverMapper.toEntity(waiverDTO);
        waiver = waiverRepository.save(waiver);
        return waiverMapper.toDto(waiver);
    }

    /**
     * Get all the waivers.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<WaiverDTO> findAll() {
        log.debug("Request to get all Waivers");
        return waiverRepository.findAll().stream()
            .map(waiverMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one waiver by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WaiverDTO> findOne(Long id) {
        log.debug("Request to get Waiver : {}", id);
        return waiverRepository.findById(id)
            .map(waiverMapper::toDto);
    }

    /**
     * Delete the waiver by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Waiver : {}", id);
        waiverRepository.deleteById(id);
    }
}
