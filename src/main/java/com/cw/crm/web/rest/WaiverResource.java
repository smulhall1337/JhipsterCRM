package com.cw.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cw.crm.service.WaiverService;
import com.cw.crm.web.rest.errors.BadRequestAlertException;
import com.cw.crm.web.rest.util.HeaderUtil;
import com.cw.crm.service.dto.WaiverDTO;
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
 * REST controller for managing Waiver.
 */
@RestController
@RequestMapping("/api")
public class WaiverResource {

    private final Logger log = LoggerFactory.getLogger(WaiverResource.class);

    private static final String ENTITY_NAME = "waiver";

    private final WaiverService waiverService;

    public WaiverResource(WaiverService waiverService) {
        this.waiverService = waiverService;
    }

    /**
     * POST  /waivers : Create a new waiver.
     *
     * @param waiverDTO the waiverDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new waiverDTO, or with status 400 (Bad Request) if the waiver has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/waivers")
    @Timed
    public ResponseEntity<WaiverDTO> createWaiver(@Valid @RequestBody WaiverDTO waiverDTO) throws URISyntaxException {
        log.debug("REST request to save Waiver : {}", waiverDTO);
        if (waiverDTO.getId() != null) {
            throw new BadRequestAlertException("A new waiver cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WaiverDTO result = waiverService.save(waiverDTO);
        return ResponseEntity.created(new URI("/api/waivers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /waivers : Updates an existing waiver.
     *
     * @param waiverDTO the waiverDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated waiverDTO,
     * or with status 400 (Bad Request) if the waiverDTO is not valid,
     * or with status 500 (Internal Server Error) if the waiverDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/waivers")
    @Timed
    public ResponseEntity<WaiverDTO> updateWaiver(@Valid @RequestBody WaiverDTO waiverDTO) throws URISyntaxException {
        log.debug("REST request to update Waiver : {}", waiverDTO);
        if (waiverDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WaiverDTO result = waiverService.save(waiverDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, waiverDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /waivers : get all the waivers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of waivers in body
     */
    @GetMapping("/waivers")
    @Timed
    public List<WaiverDTO> getAllWaivers() {
        log.debug("REST request to get all Waivers");
        return waiverService.findAll();
    }

    /**
     * GET  /waivers/:id : get the "id" waiver.
     *
     * @param id the id of the waiverDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the waiverDTO, or with status 404 (Not Found)
     */
    @GetMapping("/waivers/{id}")
    @Timed
    public ResponseEntity<WaiverDTO> getWaiver(@PathVariable Long id) {
        log.debug("REST request to get Waiver : {}", id);
        Optional<WaiverDTO> waiverDTO = waiverService.findOne(id);
        return ResponseUtil.wrapOrNotFound(waiverDTO);
    }

    /**
     * DELETE  /waivers/:id : delete the "id" waiver.
     *
     * @param id the id of the waiverDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/waivers/{id}")
    @Timed
    public ResponseEntity<Void> deleteWaiver(@PathVariable Long id) {
        log.debug("REST request to delete Waiver : {}", id);
        waiverService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
