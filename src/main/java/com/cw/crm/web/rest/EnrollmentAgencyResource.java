package com.cw.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cw.crm.service.EnrollmentAgencyService;
import com.cw.crm.web.rest.errors.BadRequestAlertException;
import com.cw.crm.web.rest.util.HeaderUtil;
import com.cw.crm.service.dto.EnrollmentAgencyDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EnrollmentAgency.
 */
@RestController
@RequestMapping("/api")
public class EnrollmentAgencyResource {

    private final Logger log = LoggerFactory.getLogger(EnrollmentAgencyResource.class);

    private static final String ENTITY_NAME = "enrollmentAgency";

    private final EnrollmentAgencyService enrollmentAgencyService;

    public EnrollmentAgencyResource(EnrollmentAgencyService enrollmentAgencyService) {
        this.enrollmentAgencyService = enrollmentAgencyService;
    }

    /**
     * POST  /enrollment-agencies : Create a new enrollmentAgency.
     *
     * @param enrollmentAgencyDTO the enrollmentAgencyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new enrollmentAgencyDTO, or with status 400 (Bad Request) if the enrollmentAgency has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/enrollment-agencies")
    @Timed
    public ResponseEntity<EnrollmentAgencyDTO> createEnrollmentAgency(@RequestBody EnrollmentAgencyDTO enrollmentAgencyDTO) throws URISyntaxException {
        log.debug("REST request to save EnrollmentAgency : {}", enrollmentAgencyDTO);
        if (enrollmentAgencyDTO.getId() != null) {
            throw new BadRequestAlertException("A new enrollmentAgency cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnrollmentAgencyDTO result = enrollmentAgencyService.save(enrollmentAgencyDTO);
        return ResponseEntity.created(new URI("/api/enrollment-agencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /enrollment-agencies : Updates an existing enrollmentAgency.
     *
     * @param enrollmentAgencyDTO the enrollmentAgencyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated enrollmentAgencyDTO,
     * or with status 400 (Bad Request) if the enrollmentAgencyDTO is not valid,
     * or with status 500 (Internal Server Error) if the enrollmentAgencyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/enrollment-agencies")
    @Timed
    public ResponseEntity<EnrollmentAgencyDTO> updateEnrollmentAgency(@RequestBody EnrollmentAgencyDTO enrollmentAgencyDTO) throws URISyntaxException {
        log.debug("REST request to update EnrollmentAgency : {}", enrollmentAgencyDTO);
        if (enrollmentAgencyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnrollmentAgencyDTO result = enrollmentAgencyService.save(enrollmentAgencyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, enrollmentAgencyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /enrollment-agencies : get all the enrollmentAgencies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of enrollmentAgencies in body
     */
    @GetMapping("/enrollment-agencies")
    @Timed
    public List<EnrollmentAgencyDTO> getAllEnrollmentAgencies() {
        log.debug("REST request to get all EnrollmentAgencies");
        return enrollmentAgencyService.findAll();
    }

    /**
     * GET  /enrollment-agencies/:id : get the "id" enrollmentAgency.
     *
     * @param id the id of the enrollmentAgencyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the enrollmentAgencyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/enrollment-agencies/{id}")
    @Timed
    public ResponseEntity<EnrollmentAgencyDTO> getEnrollmentAgency(@PathVariable Long id) {
        log.debug("REST request to get EnrollmentAgency : {}", id);
        Optional<EnrollmentAgencyDTO> enrollmentAgencyDTO = enrollmentAgencyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enrollmentAgencyDTO);
    }

    /**
     * DELETE  /enrollment-agencies/:id : delete the "id" enrollmentAgency.
     *
     * @param id the id of the enrollmentAgencyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/enrollment-agencies/{id}")
    @Timed
    public ResponseEntity<Void> deleteEnrollmentAgency(@PathVariable Long id) {
        log.debug("REST request to delete EnrollmentAgency : {}", id);
        enrollmentAgencyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
