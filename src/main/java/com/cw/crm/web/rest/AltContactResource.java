package com.cw.crm.web.rest;
import com.cw.crm.service.AltContactService;
import com.cw.crm.web.rest.errors.BadRequestAlertException;
import com.cw.crm.web.rest.util.HeaderUtil;
import com.cw.crm.service.dto.AltContactDTO;
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
 * REST controller for managing AltContact.
 */
@RestController
@RequestMapping("/api")
public class AltContactResource {

    private final Logger log = LoggerFactory.getLogger(AltContactResource.class);

    private static final String ENTITY_NAME = "altContact";

    private final AltContactService altContactService;

    public AltContactResource(AltContactService altContactService) {
        this.altContactService = altContactService;
    }

    /**
     * POST  /alt-contacts : Create a new altContact.
     *
     * @param altContactDTO the altContactDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new altContactDTO, or with status 400 (Bad Request) if the altContact has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/alt-contacts")
    public ResponseEntity<AltContactDTO> createAltContact(@Valid @RequestBody AltContactDTO altContactDTO) throws URISyntaxException {
        log.debug("REST request to save AltContact : {}", altContactDTO);
        if (altContactDTO.getId() != null) {
            throw new BadRequestAlertException("A new altContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AltContactDTO result = altContactService.save(altContactDTO);
        return ResponseEntity.created(new URI("/api/alt-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /alt-contacts : Updates an existing altContact.
     *
     * @param altContactDTO the altContactDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated altContactDTO,
     * or with status 400 (Bad Request) if the altContactDTO is not valid,
     * or with status 500 (Internal Server Error) if the altContactDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/alt-contacts")
    public ResponseEntity<AltContactDTO> updateAltContact(@Valid @RequestBody AltContactDTO altContactDTO) throws URISyntaxException {
        log.debug("REST request to update AltContact : {}", altContactDTO);
        if (altContactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AltContactDTO result = altContactService.save(altContactDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, altContactDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /alt-contacts : get all the altContacts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of altContacts in body
     */
    @GetMapping("/alt-contacts")
    public List<AltContactDTO> getAllAltContacts() {
        log.debug("REST request to get all AltContacts");
        return altContactService.findAll();
    }

    /**
     * GET  /alt-contacts/:id : get the "id" altContact.
     *
     * @param id the id of the altContactDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the altContactDTO, or with status 404 (Not Found)
     */
    @GetMapping("/alt-contacts/{id}")
    public ResponseEntity<AltContactDTO> getAltContact(@PathVariable Long id) {
        log.debug("REST request to get AltContact : {}", id);
        Optional<AltContactDTO> altContactDTO = altContactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(altContactDTO);
    }

    /**
     * DELETE  /alt-contacts/:id : delete the "id" altContact.
     *
     * @param id the id of the altContactDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/alt-contacts/{id}")
    public ResponseEntity<Void> deleteAltContact(@PathVariable Long id) {
        log.debug("REST request to delete AltContact : {}", id);
        altContactService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /partId/alt-contacts : get all the altContacts for a specific participant
     *
     * @return the ResponseEntity with status 200 (OK) and the list of altContacts in body
     */
    @GetMapping("/alt-contacts-partId/{partId}")
    public List<AltContactDTO> getAllAltContacts(@PathVariable Long partId) {
        log.debug("REST request to get all AltContacts by ParticipantId");
        return altContactService.findAllByParticipantId(partId);
    }
}
