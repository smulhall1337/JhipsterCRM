package com.cw.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cw.crm.service.ContactSubStatusService;
import com.cw.crm.web.rest.errors.BadRequestAlertException;
import com.cw.crm.web.rest.util.HeaderUtil;
import com.cw.crm.service.dto.ContactSubStatusDTO;
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
 * REST controller for managing ContactSubStatus.
 */
@RestController
@RequestMapping("/api")
public class ContactSubStatusResource {

    private final Logger log = LoggerFactory.getLogger(ContactSubStatusResource.class);

    private static final String ENTITY_NAME = "contactSubStatus";

    private final ContactSubStatusService contactSubStatusService;

    public ContactSubStatusResource(ContactSubStatusService contactSubStatusService) {
        this.contactSubStatusService = contactSubStatusService;
    }

    /**
     * POST  /contact-sub-statuses : Create a new contactSubStatus.
     *
     * @param contactSubStatusDTO the contactSubStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contactSubStatusDTO, or with status 400 (Bad Request) if the contactSubStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contact-sub-statuses")
    @Timed
    public ResponseEntity<ContactSubStatusDTO> createContactSubStatus(@Valid @RequestBody ContactSubStatusDTO contactSubStatusDTO) throws URISyntaxException {
        log.debug("REST request to save ContactSubStatus : {}", contactSubStatusDTO);
        if (contactSubStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new contactSubStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactSubStatusDTO result = contactSubStatusService.save(contactSubStatusDTO);
        return ResponseEntity.created(new URI("/api/contact-sub-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contact-sub-statuses : Updates an existing contactSubStatus.
     *
     * @param contactSubStatusDTO the contactSubStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contactSubStatusDTO,
     * or with status 400 (Bad Request) if the contactSubStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the contactSubStatusDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contact-sub-statuses")
    @Timed
    public ResponseEntity<ContactSubStatusDTO> updateContactSubStatus(@Valid @RequestBody ContactSubStatusDTO contactSubStatusDTO) throws URISyntaxException {
        log.debug("REST request to update ContactSubStatus : {}", contactSubStatusDTO);
        if (contactSubStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContactSubStatusDTO result = contactSubStatusService.save(contactSubStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contactSubStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contact-sub-statuses : get all the contactSubStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contactSubStatuses in body
     */
    @GetMapping("/contact-sub-statuses")
    @Timed
    public List<ContactSubStatusDTO> getAllContactSubStatuses() {
        log.debug("REST request to get all ContactSubStatuses");
        return contactSubStatusService.findAll();
    }

    /**
     * GET  /contact-sub-statuses/:id : get the "id" contactSubStatus.
     *
     * @param id the id of the contactSubStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contactSubStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/contact-sub-statuses/{id}")
    @Timed
    public ResponseEntity<ContactSubStatusDTO> getContactSubStatus(@PathVariable Long id) {
        log.debug("REST request to get ContactSubStatus : {}", id);
        Optional<ContactSubStatusDTO> contactSubStatusDTO = contactSubStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactSubStatusDTO);
    }

    /**
     * DELETE  /contact-sub-statuses/:id : delete the "id" contactSubStatus.
     *
     * @param id the id of the contactSubStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contact-sub-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteContactSubStatus(@PathVariable Long id) {
        log.debug("REST request to delete ContactSubStatus : {}", id);
        contactSubStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
