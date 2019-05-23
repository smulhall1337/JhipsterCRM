package com.cw.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cw.crm.service.ReferralTypeService;
import com.cw.crm.web.rest.errors.BadRequestAlertException;
import com.cw.crm.web.rest.util.HeaderUtil;
import com.cw.crm.service.dto.ReferralTypeDTO;
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
 * REST controller for managing ReferralType.
 */
@RestController
@RequestMapping("/api")
public class ReferralTypeResource {

    private final Logger log = LoggerFactory.getLogger(ReferralTypeResource.class);

    private static final String ENTITY_NAME = "referralType";

    private final ReferralTypeService referralTypeService;

    public ReferralTypeResource(ReferralTypeService referralTypeService) {
        this.referralTypeService = referralTypeService;
    }

    /**
     * POST  /referral-types : Create a new referralType.
     *
     * @param referralTypeDTO the referralTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new referralTypeDTO, or with status 400 (Bad Request) if the referralType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/referral-types")
    @Timed
    public ResponseEntity<ReferralTypeDTO> createReferralType(@Valid @RequestBody ReferralTypeDTO referralTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ReferralType : {}", referralTypeDTO);
        if (referralTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new referralType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReferralTypeDTO result = referralTypeService.save(referralTypeDTO);
        return ResponseEntity.created(new URI("/api/referral-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /referral-types : Updates an existing referralType.
     *
     * @param referralTypeDTO the referralTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated referralTypeDTO,
     * or with status 400 (Bad Request) if the referralTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the referralTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/referral-types")
    @Timed
    public ResponseEntity<ReferralTypeDTO> updateReferralType(@Valid @RequestBody ReferralTypeDTO referralTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ReferralType : {}", referralTypeDTO);
        if (referralTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReferralTypeDTO result = referralTypeService.save(referralTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, referralTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /referral-types : get all the referralTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of referralTypes in body
     */
    @GetMapping("/referral-types")
    @Timed
    public List<ReferralTypeDTO> getAllReferralTypes() {
        log.debug("REST request to get all ReferralTypes");
        return referralTypeService.findAll();
    }

    /**
     * GET  /referral-types/:id : get the "id" referralType.
     *
     * @param id the id of the referralTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the referralTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/referral-types/{id}")
    @Timed
    public ResponseEntity<ReferralTypeDTO> getReferralType(@PathVariable Long id) {
        log.debug("REST request to get ReferralType : {}", id);
        Optional<ReferralTypeDTO> referralTypeDTO = referralTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(referralTypeDTO);
    }

    /**
     * DELETE  /referral-types/:id : delete the "id" referralType.
     *
     * @param id the id of the referralTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/referral-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteReferralType(@PathVariable Long id) {
        log.debug("REST request to delete ReferralType : {}", id);
        referralTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
