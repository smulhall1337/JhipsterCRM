package com.cw.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cw.crm.service.ReferralSourceService;
import com.cw.crm.web.rest.errors.BadRequestAlertException;
import com.cw.crm.web.rest.util.HeaderUtil;
import com.cw.crm.service.dto.ReferralSourceDTO;
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
 * REST controller for managing ReferralSource.
 */
@RestController
@RequestMapping("/api")
public class ReferralSourceResource {

    private final Logger log = LoggerFactory.getLogger(ReferralSourceResource.class);

    private static final String ENTITY_NAME = "referralSource";

    private final ReferralSourceService referralSourceService;

    public ReferralSourceResource(ReferralSourceService referralSourceService) {
        this.referralSourceService = referralSourceService;
    }

    /**
     * POST  /referral-sources : Create a new referralSource.
     *
     * @param referralSourceDTO the referralSourceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new referralSourceDTO, or with status 400 (Bad Request) if the referralSource has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/referral-sources")
    @Timed
    public ResponseEntity<ReferralSourceDTO> createReferralSource(@Valid @RequestBody ReferralSourceDTO referralSourceDTO) throws URISyntaxException {
        log.debug("REST request to save ReferralSource : {}", referralSourceDTO);
        if (referralSourceDTO.getId() != null) {
            throw new BadRequestAlertException("A new referralSource cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReferralSourceDTO result = referralSourceService.save(referralSourceDTO);
        return ResponseEntity.created(new URI("/api/referral-sources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /referral-sources : Updates an existing referralSource.
     *
     * @param referralSourceDTO the referralSourceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated referralSourceDTO,
     * or with status 400 (Bad Request) if the referralSourceDTO is not valid,
     * or with status 500 (Internal Server Error) if the referralSourceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/referral-sources")
    @Timed
    public ResponseEntity<ReferralSourceDTO> updateReferralSource(@Valid @RequestBody ReferralSourceDTO referralSourceDTO) throws URISyntaxException {
        log.debug("REST request to update ReferralSource : {}", referralSourceDTO);
        if (referralSourceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReferralSourceDTO result = referralSourceService.save(referralSourceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, referralSourceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /referral-sources : get all the referralSources.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of referralSources in body
     */
    @GetMapping("/referral-sources")
    @Timed
    public List<ReferralSourceDTO> getAllReferralSources() {
        log.debug("REST request to get all ReferralSources");
        return referralSourceService.findAll();
    }

    /**
     * GET  /referral-sources/:id : get the "id" referralSource.
     *
     * @param id the id of the referralSourceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the referralSourceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/referral-sources/{id}")
    @Timed
    public ResponseEntity<ReferralSourceDTO> getReferralSource(@PathVariable Long id) {
        log.debug("REST request to get ReferralSource : {}", id);
        Optional<ReferralSourceDTO> referralSourceDTO = referralSourceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(referralSourceDTO);
    }

    /**
     * DELETE  /referral-sources/:id : delete the "id" referralSource.
     *
     * @param id the id of the referralSourceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/referral-sources/{id}")
    @Timed
    public ResponseEntity<Void> deleteReferralSource(@PathVariable Long id) {
        log.debug("REST request to delete ReferralSource : {}", id);
        referralSourceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
