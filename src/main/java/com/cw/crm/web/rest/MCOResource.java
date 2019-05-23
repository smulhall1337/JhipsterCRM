package com.cw.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cw.crm.service.MCOService;
import com.cw.crm.web.rest.errors.BadRequestAlertException;
import com.cw.crm.web.rest.util.HeaderUtil;
import com.cw.crm.service.dto.MCODTO;
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
 * REST controller for managing MCO.
 */
@RestController
@RequestMapping("/api")
public class MCOResource {

    private final Logger log = LoggerFactory.getLogger(MCOResource.class);

    private static final String ENTITY_NAME = "mCO";

    private final MCOService mCOService;

    public MCOResource(MCOService mCOService) {
        this.mCOService = mCOService;
    }

    /**
     * POST  /mcos : Create a new mCO.
     *
     * @param mCODTO the mCODTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mCODTO, or with status 400 (Bad Request) if the mCO has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mcos")
    @Timed
    public ResponseEntity<MCODTO> createMCO(@Valid @RequestBody MCODTO mCODTO) throws URISyntaxException {
        log.debug("REST request to save MCO : {}", mCODTO);
        if (mCODTO.getId() != null) {
            throw new BadRequestAlertException("A new mCO cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MCODTO result = mCOService.save(mCODTO);
        return ResponseEntity.created(new URI("/api/mcos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mcos : Updates an existing mCO.
     *
     * @param mCODTO the mCODTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mCODTO,
     * or with status 400 (Bad Request) if the mCODTO is not valid,
     * or with status 500 (Internal Server Error) if the mCODTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mcos")
    @Timed
    public ResponseEntity<MCODTO> updateMCO(@Valid @RequestBody MCODTO mCODTO) throws URISyntaxException {
        log.debug("REST request to update MCO : {}", mCODTO);
        if (mCODTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MCODTO result = mCOService.save(mCODTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mCODTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mcos : get all the mCOS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of mCOS in body
     */
    @GetMapping("/mcos")
    @Timed
    public List<MCODTO> getAllMCOS() {
        log.debug("REST request to get all MCOS");
        return mCOService.findAll();
    }

    /**
     * GET  /mcos/:id : get the "id" mCO.
     *
     * @param id the id of the mCODTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mCODTO, or with status 404 (Not Found)
     */
    @GetMapping("/mcos/{id}")
    @Timed
    public ResponseEntity<MCODTO> getMCO(@PathVariable Long id) {
        log.debug("REST request to get MCO : {}", id);
        Optional<MCODTO> mCODTO = mCOService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mCODTO);
    }

    /**
     * DELETE  /mcos/:id : delete the "id" mCO.
     *
     * @param id the id of the mCODTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mcos/{id}")
    @Timed
    public ResponseEntity<Void> deleteMCO(@PathVariable Long id) {
        log.debug("REST request to delete MCO : {}", id);
        mCOService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
