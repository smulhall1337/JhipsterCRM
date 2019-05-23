package com.cw.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cw.crm.service.ContactStatusService;
import com.cw.crm.web.rest.errors.BadRequestAlertException;
import com.cw.crm.web.rest.util.HeaderUtil;
import com.cw.crm.service.dto.ContactStatusDTO;
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
 * REST controller for managing ContactStatus.
 */
@RestController
@RequestMapping("/api")
public class ContactStatusResource {

    private final Logger log = LoggerFactory.getLogger(ContactStatusResource.class);

    private static final String ENTITY_NAME = "contactStatus";

    private final ContactStatusService contactStatusService;

    public ContactStatusResource(ContactStatusService contactStatusService) {
        this.contactStatusService = contactStatusService;
    }

    /**
     * POST  /contact-statuses : Create a new contactStatus.
     *
     * @param contactStatusDTO the contactStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contactStatusDTO, or with status 400 (Bad Request) if the contactStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contact-statuses")
    @Timed
    public ResponseEntity<ContactStatusDTO> createContactStatus(@Valid @RequestBody ContactStatusDTO contactStatusDTO) throws URISyntaxException {
        log.debug("REST request to save ContactStatus : {}", contactStatusDTO);
        if (contactStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new contactStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactStatusDTO result = contactStatusService.save(contactStatusDTO);
        return ResponseEntity.created(new URI("/api/contact-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contact-statuses : Updates an existing contactStatus.
     *
     * @param contactStatusDTO the contactStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contactStatusDTO,
     * or with status 400 (Bad Request) if the contactStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the contactStatusDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contact-statuses")
    @Timed
    public ResponseEntity<ContactStatusDTO> updateContactStatus(@Valid @RequestBody ContactStatusDTO contactStatusDTO) throws URISyntaxException {
        log.debug("REST request to update ContactStatus : {}", contactStatusDTO);
        if (contactStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContactStatusDTO result = contactStatusService.save(contactStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contactStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contact-statuses : get all the contactStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contactStatuses in body
     */
    @GetMapping("/contact-statuses")
    @Timed
    public List<ContactStatusDTO> getAllContactStatuses() {
        log.debug("REST request to get all ContactStatuses");
        return contactStatusService.findAll();
    }

    /**
     * GET  /contact-statuses/:id : get the "id" contactStatus.
     *
     * @param id the id of the contactStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contactStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/contact-statuses/{id}")
    @Timed
    public ResponseEntity<ContactStatusDTO> getContactStatus(@PathVariable Long id) {
        log.debug("REST request to get ContactStatus : {}", id);
        Optional<ContactStatusDTO> contactStatusDTO = contactStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactStatusDTO);
    }

    /**
     * DELETE  /contact-statuses/:id : delete the "id" contactStatus.
     *
     * @param id the id of the contactStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contact-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteContactStatus(@PathVariable Long id) {
        log.debug("REST request to delete ContactStatus : {}", id);
        contactStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
