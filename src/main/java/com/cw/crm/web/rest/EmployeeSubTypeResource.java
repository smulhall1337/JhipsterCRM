package com.cw.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cw.crm.service.EmployeeSubTypeService;
import com.cw.crm.web.rest.errors.BadRequestAlertException;
import com.cw.crm.web.rest.util.HeaderUtil;
import com.cw.crm.service.dto.EmployeeSubTypeDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EmployeeSubType.
 */
@RestController
@RequestMapping("/api")
public class EmployeeSubTypeResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeSubTypeResource.class);

    private static final String ENTITY_NAME = "employeeSubType";

    private final EmployeeSubTypeService employeeSubTypeService;

    public EmployeeSubTypeResource(EmployeeSubTypeService employeeSubTypeService) {
        this.employeeSubTypeService = employeeSubTypeService;
    }

    /**
     * POST  /employee-sub-types : Create a new employeeSubType.
     *
     * @param employeeSubTypeDTO the employeeSubTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employeeSubTypeDTO, or with status 400 (Bad Request) if the employeeSubType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/employee-sub-types")
    @Timed
    public ResponseEntity<EmployeeSubTypeDTO> createEmployeeSubType(@Valid @RequestBody EmployeeSubTypeDTO employeeSubTypeDTO) throws URISyntaxException {
        log.debug("REST request to save EmployeeSubType : {}", employeeSubTypeDTO);
        if (employeeSubTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeeSubType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeSubTypeDTO result = employeeSubTypeService.save(employeeSubTypeDTO);
        return ResponseEntity.created(new URI("/api/employee-sub-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employee-sub-types : Updates an existing employeeSubType.
     *
     * @param employeeSubTypeDTO the employeeSubTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employeeSubTypeDTO,
     * or with status 400 (Bad Request) if the employeeSubTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the employeeSubTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/employee-sub-types")
    @Timed
    public ResponseEntity<EmployeeSubTypeDTO> updateEmployeeSubType(@Valid @RequestBody EmployeeSubTypeDTO employeeSubTypeDTO) throws URISyntaxException {
        log.debug("REST request to update EmployeeSubType : {}", employeeSubTypeDTO);
        if (employeeSubTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeSubTypeDTO result = employeeSubTypeService.save(employeeSubTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, employeeSubTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employee-sub-types : get all the employeeSubTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of employeeSubTypes in body
     */
    @GetMapping("/employee-sub-types")
    @Timed
    public List<EmployeeSubTypeDTO> getAllEmployeeSubTypes() {
        log.debug("REST request to get all EmployeeSubTypes");
        return employeeSubTypeService.findAll();
    }

    /**
     * GET  /employee-sub-types/:id : get the "id" employeeSubType.
     *
     * @param id the id of the employeeSubTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employeeSubTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/employee-sub-types/{id}")
    @Timed
    public ResponseEntity<EmployeeSubTypeDTO> getEmployeeSubType(@PathVariable Long id) {
        log.debug("REST request to get EmployeeSubType : {}", id);
        Optional<EmployeeSubTypeDTO> employeeSubTypeDTO = employeeSubTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeSubTypeDTO);
    }

    /**
     * DELETE  /employee-sub-types/:id : delete the "id" employeeSubType.
     *
     * @param id the id of the employeeSubTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/employee-sub-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmployeeSubType(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeSubType : {}", id);
        employeeSubTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
