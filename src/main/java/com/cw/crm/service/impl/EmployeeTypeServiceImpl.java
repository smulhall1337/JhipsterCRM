package com.cw.crm.service.impl;

import com.cw.crm.service.EmployeeTypeService;
import com.cw.crm.domain.EmployeeType;
import com.cw.crm.repository.EmployeeTypeRepository;
import com.cw.crm.service.dto.EmployeeTypeDTO;
import com.cw.crm.service.mapper.EmployeeTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing EmployeeType.
 */
@Service
@Transactional
public class EmployeeTypeServiceImpl implements EmployeeTypeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeTypeServiceImpl.class);

    private final EmployeeTypeRepository employeeTypeRepository;

    private final EmployeeTypeMapper employeeTypeMapper;

    public EmployeeTypeServiceImpl(EmployeeTypeRepository employeeTypeRepository, EmployeeTypeMapper employeeTypeMapper) {
        this.employeeTypeRepository = employeeTypeRepository;
        this.employeeTypeMapper = employeeTypeMapper;
    }

    /**
     * Save a employeeType.
     *
     * @param employeeTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EmployeeTypeDTO save(EmployeeTypeDTO employeeTypeDTO) {
        log.debug("Request to save EmployeeType : {}", employeeTypeDTO);

        EmployeeType employeeType = employeeTypeMapper.toEntity(employeeTypeDTO);
        employeeType = employeeTypeRepository.save(employeeType);
        return employeeTypeMapper.toDto(employeeType);
    }

    /**
     * Get all the employeeTypes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeTypeDTO> findAll() {
        log.debug("Request to get all EmployeeTypes");
        return employeeTypeRepository.findAll().stream()
            .map(employeeTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one employeeType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeTypeDTO> findOne(Long id) {
        log.debug("Request to get EmployeeType : {}", id);
        return employeeTypeRepository.findById(id)
            .map(employeeTypeMapper::toDto);
    }

    /**
     * Delete the employeeType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmployeeType : {}", id);
        employeeTypeRepository.deleteById(id);
    }
}
