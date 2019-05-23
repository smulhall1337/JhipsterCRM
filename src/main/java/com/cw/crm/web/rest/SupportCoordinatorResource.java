package com.cw.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cw.crm.service.SupportCoordinatorService;
import com.cw.crm.web.rest.errors.BadRequestAlertException;
import com.cw.crm.web.rest.util.HeaderUtil;
import com.cw.crm.web.rest.util.PaginationUtil;
import com.cw.crm.service.dto.SupportCoordinatorDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SupportCoordinator.
 */
@RestController
@RequestMapping("/api")
public class SupportCoordinatorResource {

    private final Logger log = LoggerFactory.getLogger(SupportCoordinatorResource.class);

    private static final String ENTITY_NAME = "supportCoordinator";

    private final SupportCoordinatorService supportCoordinatorService;

    public SupportCoordinatorResource(SupportCoordinatorService supportCoordinatorService) {
        this.supportCoordinatorService = supportCoordinatorService;
    }

    /**
     * POST  /support-coordinators : Create a new supportCoordinator.
     *
     * @param supportCoordinatorDTO the supportCoordinatorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new supportCoordinatorDTO, or with status 400 (Bad Request) if the supportCoordinator has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/support-coordinators")
    @Timed
    public ResponseEntity<SupportCoordinatorDTO> createSupportCoordinator(@Valid @RequestBody SupportCoordinatorDTO supportCoordinatorDTO) throws URISyntaxException {
        log.debug("REST request to save SupportCoordinator : {}", supportCoordinatorDTO);
        if (supportCoordinatorDTO.getId() != null) {
            throw new BadRequestAlertException("A new supportCoordinator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupportCoordinatorDTO result = supportCoordinatorService.save(supportCoordinatorDTO);
        return ResponseEntity.created(new URI("/api/support-coordinators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /support-coordinators : Updates an existing supportCoordinator.
     *
     * @param supportCoordinatorDTO the supportCoordinatorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated supportCoordinatorDTO,
     * or with status 400 (Bad Request) if the supportCoordinatorDTO is not valid,
     * or with status 500 (Internal Server Error) if the supportCoordinatorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/support-coordinators")
    @Timed
    public ResponseEntity<SupportCoordinatorDTO> updateSupportCoordinator(@Valid @RequestBody SupportCoordinatorDTO supportCoordinatorDTO) throws URISyntaxException {
        log.debug("REST request to update SupportCoordinator : {}", supportCoordinatorDTO);
        if (supportCoordinatorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupportCoordinatorDTO result = supportCoordinatorService.save(supportCoordinatorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supportCoordinatorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /support-coordinators : get all the supportCoordinators.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of supportCoordinators in body
     */
    @GetMapping("/support-coordinators")
    @Timed
    public ResponseEntity<List<SupportCoordinatorDTO>> getAllSupportCoordinators(Pageable pageable) {
        log.debug("REST request to get a page of SupportCoordinators");
        Page<SupportCoordinatorDTO> page = supportCoordinatorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/support-coordinators");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /support-coordinators/:id : get the "id" supportCoordinator.
     *
     * @param id the id of the supportCoordinatorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the supportCoordinatorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/support-coordinators/{id}")
    @Timed
    public ResponseEntity<SupportCoordinatorDTO> getSupportCoordinator(@PathVariable Long id) {
        log.debug("REST request to get SupportCoordinator : {}", id);
        Optional<SupportCoordinatorDTO> supportCoordinatorDTO = supportCoordinatorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supportCoordinatorDTO);
    }

    /**
     * DELETE  /support-coordinators/:id : delete the "id" supportCoordinator.
     *
     * @param id the id of the supportCoordinatorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/support-coordinators/{id}")
    @Timed
    public ResponseEntity<Void> deleteSupportCoordinator(@PathVariable Long id) {
        log.debug("REST request to delete SupportCoordinator : {}", id);
        supportCoordinatorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
