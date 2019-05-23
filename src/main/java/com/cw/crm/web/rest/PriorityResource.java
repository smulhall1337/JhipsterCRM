package com.cw.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cw.crm.service.PriorityService;
import com.cw.crm.web.rest.errors.BadRequestAlertException;
import com.cw.crm.web.rest.util.HeaderUtil;
import com.cw.crm.service.dto.PriorityDTO;
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
 * REST controller for managing Priority.
 */
@RestController
@RequestMapping("/api")
public class PriorityResource {

    private final Logger log = LoggerFactory.getLogger(PriorityResource.class);

    private static final String ENTITY_NAME = "priority";

    private final PriorityService priorityService;

    public PriorityResource(PriorityService priorityService) {
        this.priorityService = priorityService;
    }

    /**
     * POST  /priorities : Create a new priority.
     *
     * @param priorityDTO the priorityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new priorityDTO, or with status 400 (Bad Request) if the priority has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/priorities")
    @Timed
    public ResponseEntity<PriorityDTO> createPriority(@Valid @RequestBody PriorityDTO priorityDTO) throws URISyntaxException {
        log.debug("REST request to save Priority : {}", priorityDTO);
        if (priorityDTO.getId() != null) {
            throw new BadRequestAlertException("A new priority cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PriorityDTO result = priorityService.save(priorityDTO);
        return ResponseEntity.created(new URI("/api/priorities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /priorities : Updates an existing priority.
     *
     * @param priorityDTO the priorityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated priorityDTO,
     * or with status 400 (Bad Request) if the priorityDTO is not valid,
     * or with status 500 (Internal Server Error) if the priorityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/priorities")
    @Timed
    public ResponseEntity<PriorityDTO> updatePriority(@Valid @RequestBody PriorityDTO priorityDTO) throws URISyntaxException {
        log.debug("REST request to update Priority : {}", priorityDTO);
        if (priorityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PriorityDTO result = priorityService.save(priorityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, priorityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /priorities : get all the priorities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of priorities in body
     */
    @GetMapping("/priorities")
    @Timed
    public List<PriorityDTO> getAllPriorities() {
        log.debug("REST request to get all Priorities");
        return priorityService.findAll();
    }

    /**
     * GET  /priorities/:id : get the "id" priority.
     *
     * @param id the id of the priorityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the priorityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/priorities/{id}")
    @Timed
    public ResponseEntity<PriorityDTO> getPriority(@PathVariable Long id) {
        log.debug("REST request to get Priority : {}", id);
        Optional<PriorityDTO> priorityDTO = priorityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(priorityDTO);
    }

    /**
     * DELETE  /priorities/:id : delete the "id" priority.
     *
     * @param id the id of the priorityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/priorities/{id}")
    @Timed
    public ResponseEntity<Void> deletePriority(@PathVariable Long id) {
        log.debug("REST request to delete Priority : {}", id);
        priorityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
